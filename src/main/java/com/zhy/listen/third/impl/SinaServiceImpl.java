package com.zhy.listen.third.impl;

import org.springframework.stereotype.Service;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;

import com.zhy.listen.bean.third.Sina;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.third.AuthorizeService;
import com.zhy.listen.util.AreaUtil;

@Service("sinaService")
public class SinaServiceImpl implements AuthorizeService {

    @Override
    public String authorize() throws Exception {
        Oauth oauth = new Oauth();
        return oauth.authorize("code");
    }

    /**
     * 获取新浪微博数据<br>
     * <br>
     * 
     * 用户�?�?<br>
     * 用户昵称<br>
     * 用户头像<br>
     * 用户省份<br>
     * 用户�?在城�?<br>
     * 用户个�?�标�?<br>
     * 
     */
    @Override
    public User getUserFromThird(Third third) throws Exception {
        Sina sina = (Sina) third;
        AccessToken token = new Oauth().getAccessTokenByCode(sina.getCode());
        sina.setToken(token.getAccessToken());
        //sina.setEndTime(System.currentTimeMillis() + Long.parseLong(token.getExpireIn()) * 1000);
        Weibo weibo = new Weibo();
        weibo.setToken(token.getAccessToken());

        // sina user id
        String uid = new Account().getUid().get("uid").toString();
        weibo4j.model.User sinaUser = new Users().showUserById(uid);
        User user = new User();
        user.setUserNick(sinaUser.getScreenName());
        user.setIntroduction(sinaUser.getDescription());
        String[] area = AreaUtil
                .getSinaArea(String.valueOf(sinaUser.getProvince()), String.valueOf(sinaUser.getCity()));
        user.setProvince(area[0]);
        user.setCity(area[1]);
        user.setUserImg(sinaUser.getProfileImageUrl());
        // TODO 获取标签 并存入标签库 等�?�辑
        // List<Tag> tags = new Tags().getTags(uid);
        return user;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        Sina sina = (Sina) third;
        Weibo weibo = new Weibo();
        weibo.setToken(sina.getToken());
        return new Timeline().UpdateStatus(message) != null ? true : false;
    }

}
