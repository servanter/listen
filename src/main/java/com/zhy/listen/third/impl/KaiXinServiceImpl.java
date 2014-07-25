package com.zhy.listen.third.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import kx2_4j.apis.Records;
import kx2_4j.apis.Users;
import kx2_4j.http.Response;
import kx2_4j.model.KxModel;
import kx2_4j.oauth2.AccessToken;
import kx2_4j.oauth2.KxOAuth;

import com.zhy.listen.bean.third.KaiXin;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.third.AuthorizeService;

@Service("kaiXinService")
public class KaiXinServiceImpl implements AuthorizeService {

    public static final String KAIXINDISPLAY = "page";
    @Override
    public String authorize() throws Exception {
        return new KxOAuth().getAuthorizeURLforCode("", "page");
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        KaiXin kaiXin = (KaiXin) third;
        KxOAuth connection = new KxOAuth();
        AccessToken accessToken = connection.getOAuthAccessTokenFromCode(kaiXin.getCode());
        Users users = new Users(accessToken.getToken());
        Response response = users.getCurUser("uid,name,gender,hometown,city,birthday,intro");
        kx2_4j.model.users.User kaiXinUser = KxModel.parseForUsersMe(response);
        User result = new User();
        result.setUserNick(kaiXinUser.getName());
        result.setSex(kaiXinUser.getGender());
        result.setProvince(kaiXinUser.getHometown());
        result.setCity(kaiXinUser.getCity());
        if(kaiXinUser.getBirthday() != null && kaiXinUser.getBirthday().length() > 0){
            result.setBirthday(Timestamp.valueOf(kaiXinUser.getBirthday()));
        }
        result.setIntroduction(kaiXinUser.getIntro());
        kaiXin.setToken(accessToken.getToken());
        return result;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        KaiXin kaiXin = (KaiXin) third;
        Records records = new Records(kaiXin.getToken());
        Response response = records.addRecord(message, 0, "", 0f, 0f, 1, 0, "", "");
        return false;
    }

}
