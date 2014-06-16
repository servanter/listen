package com.zhy.listen.third.impl;

import org.springframework.stereotype.Service;

import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.utils.QStrOperate;
import com.zhy.listen.bean.Third;
import com.zhy.listen.bean.User;
import com.zhy.listen.bean.third.QQWeiBo;
import com.zhy.listen.third.AuthorizeService;

@Service("qqWeiboSerivce")
public class QQWeiboSerivceImpl implements AuthorizeService {

    public static final String FORMAT = "json";

    @Override
    public String authorize() throws Exception {
        OAuthV2 authV2 = new OAuthV2();
        authV2.setResponseType("code");
        String queryString = QStrOperate.getQueryString(authV2.getAuthorizationParamsList());
        String urlWithQueryString = OAuthConstants.OAUTH_V2_AUTHORIZE_URL + "?" + queryString;
        return urlWithQueryString;
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        QQWeiBo qqWeiBo = (QQWeiBo) third;
        OAuthV2 authV2 = new OAuthV2();
        authV2.setGrantType("authorize_code");
        authV2.setAuthorizeCode(qqWeiBo.getCode());
        authV2.setOpenid(qqWeiBo.getOpenId());
        authV2.setOpenkey(qqWeiBo.getOpenKey());
        OAuthV2Client.accessToken(authV2);
        System.out.println(authV2.getAccessToken());
        UserAPI userAPI = new UserAPI(authV2.getOauthVersion());
        System.out.println(userAPI.info(authV2, FORMAT));
        return null;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
