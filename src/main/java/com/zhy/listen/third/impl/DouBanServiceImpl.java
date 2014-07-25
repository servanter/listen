package com.zhy.listen.third.impl;

import org.springframework.stereotype.Service;

import com.dongxuexidu.douban4j.constants.DefaultConfigs;
import com.dongxuexidu.douban4j.model.app.AccessToken;
import com.dongxuexidu.douban4j.model.app.RequestGrantScope;
import com.dongxuexidu.douban4j.model.user.DoubanUserObj;
import com.dongxuexidu.douban4j.provider.OAuthDoubanProvider;
import com.dongxuexidu.douban4j.service.DoubanShuoService;
import com.dongxuexidu.douban4j.service.DoubanUserService;
import com.zhy.listen.bean.third.DouBan;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.third.AuthorizeService;

@Service("douBanService")
public class DouBanServiceImpl implements AuthorizeService {

    @Override
    public String authorize() throws Exception {
        OAuthDoubanProvider oauth = new OAuthDoubanProvider();
        oauth.addScope(RequestGrantScope.BASIC_COMMON_SCOPE).addScope(RequestGrantScope.SHUO_WRITE_SCOPE);
        oauth.setRedirectUrl("http://127.0.0.1:8080/libra/third/callback.do");
        return oauth.getGetCodeRedirectUrl();
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        DouBan douBan = (DouBan)third;
        OAuthDoubanProvider oauth = new OAuthDoubanProvider();
        AccessToken accessToken = oauth.tradeAccessTokenWithCode(douBan.getCode());
        DoubanUserService doubanUserService = new DoubanUserService();
        DoubanUserObj douBanUser = doubanUserService.getLoggedInUserProfile(accessToken.getAccessToken());
        
        // 用户�?
        System.out.println(douBanUser.getTitle());
        User user = new User();
        return user;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        DouBan douBan = (DouBan)third;
        DoubanShuoService doubanShuoService = new DoubanShuoService();
        System.out.println(doubanShuoService.postNewStatus(message, null, DefaultConfigs.API_KEY, douBan.getToken()));
        return false;
    }

}
