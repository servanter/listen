package com.zhy.listen.third.impl;

import org.springframework.stereotype.Service;

import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.Share;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.GeneralResultBean;
import com.qq.connect.oauth.Oauth;
import com.zhy.listen.bean.third.QQ;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.third.AuthorizeService;

@Service("qQService")
public class QqServiceImpl implements AuthorizeService {

    @Override
    public String authorize() throws Exception {
        return null;
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        QQ qq = (QQ) third;
        User user = new User();
        AccessToken accessToken = new Oauth().getAccessTokenByRequest(qq.getRequest());
        if (accessToken.getAccessToken() != null && accessToken.getAccessToken().length() > 0) {
            OpenID openID = new OpenID(accessToken.getAccessToken());

            // qq平台�?�?
            String userOpenId = openID.getUserOpenID();
            qq.setToken(accessToken.getAccessToken());
            qq.setOpenId(userOpenId);
            UserInfo qzoneUserInfo = new UserInfo(accessToken.getAccessToken(), userOpenId);
            user.setUserNick(qzoneUserInfo.getUserInfo().getNickname());
        }
        return user;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        QQ qq = (QQ) third;
        Share share = new Share(qq.getToken(), qq.getOpenId());
        String shareUrl = ""; // 分享链接
        String size = "投投�?"; // 来源名称
        String fromUrl = ""; // 来源地址
        String parameters = ""; // 参数(可做统计)
        GeneralResultBean result = share.addShare(message, shareUrl, size, fromUrl, parameters);
        return result.getRet() == 0;
    }

}
