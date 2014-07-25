package com.zhy.listen.third.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t4j.OAuthVersion;
import t4j.TBlog;
import t4j.http.OAuth2AccessToken;

import com.zhy.listen.bean.third.WangYi;
import com.zhy.listen.common.Constant;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.third.AuthorizeService;

@Service("wangYiService")
public class WangYiServiceImpl implements AuthorizeService {

    @Autowired
    private TemplateService templateService;

    @Override
    public String authorize() throws Exception {
        TBlog blog = new TBlog(OAuthVersion.V2);
        blog.setRedirectURL(templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS));
        return blog.getOAuth2AuthorizeURL();
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        WangYi wangYi = (WangYi) third;
        TBlog blog = new TBlog(OAuthVersion.V2);
        blog.setRedirectURL(templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS));
        OAuth2AccessToken accToken = blog.getOAuth2AccessTokenByCode(wangYi.getCode());
        wangYi.setToken(accToken.getAccess_token());
        blog.setOAuth2AccessToken(accToken.getAccess_token());
        t4j.data.User tUser = blog.verifyCredentials();

        User user = new User();
        user.setUserNick(tUser.getName());
        if (tUser.getUserTag() != null && tUser.getUserTag().length > 0) {
            // user.setTags();
        }
        return user;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        WangYi wangYi = (WangYi) third;
        TBlog blog = new TBlog(OAuthVersion.V2);
        blog.setRedirectURL(templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS));
        blog.setOAuth2AccessToken(wangYi.getToken());
        return blog.updateStatus(message) != null;
    }

}
