package com.zhy.listen.third.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t4j.sohu.OAuthVersion;
import t4j.sohu.TBlog;
import t4j.sohu.http.OAuth2AccessToken;

import com.zhy.listen.bean.Third;
import com.zhy.listen.bean.User;
import com.zhy.listen.bean.third.Sohu;
import com.zhy.listen.common.Constant;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.third.AuthorizeService;

@Service("soHuService")
public class SoHuServiceImpl implements AuthorizeService {

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
        Sohu sohu = (Sohu)third;
        
        TBlog blog = new TBlog(OAuthVersion.V2);
        blog.setRedirectURL(templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS));
        OAuth2AccessToken accToken = blog.getOAuth2AccessTokenByCode(sohu.getCode());
        User user = new User();
        return user;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        return false;
    }

}
