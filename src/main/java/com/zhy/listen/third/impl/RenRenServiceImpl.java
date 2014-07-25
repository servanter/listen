package com.zhy.listen.third.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.HttpURLUtils;
import com.zhy.listen.bean.third.RenRen;
import com.zhy.listen.common.Constant;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.third.AuthorizeService;

@Service("renrenService")
public class RenRenServiceImpl implements AuthorizeService {

    public static final String OAUTH_URL = "https://graph.renren.com/oauth/authorize?";

    @Autowired
    private TemplateService templateService;
    
    @Override
    public String authorize() throws Exception {
        return OAUTH_URL + "client_id=" + RenrenApiConfig.renrenAppID + "&response_type=code" + "&redirect_uri="
                + URLEncoder.encode(templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS), "UTF-8") + "&display=page";
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        User user = null;
        RenRen renren = (RenRen) third;
        String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/token";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", RenrenApiConfig.renrenApiKey);
        parameters.put("client_secret", RenrenApiConfig.renrenApiSecret);
        parameters.put("redirect_uri", templateService.getMessage(Constant.TEMPLATE_THIRD_CALLBACK_WORDS));
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", renren.getCode());

        // request renren and get accesstoken
        String tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint, parameters);
        JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
        if (tokenJson != null) {
            String accessToken = (String) tokenJson.get("access_token");
            Long expiresIn = (Long) tokenJson.get("expires_in");
            long currentTime = System.currentTimeMillis() / 1000;
            long expiresTime = currentTime + expiresIn;

            renren.setToken(accessToken);
            //renren.setEndTime(expiresTime);

            // invoke renren api
            RenrenApiClient apiClient = RenrenApiClient.getInstance();
            int rrUid = apiClient.getUserService().getLoggedInUser(new AccessToken(accessToken));
            JSONArray users = apiClient.getUserService()
                    .getInfo(String.valueOf(rrUid), new AccessToken(accessToken));
            if(users.size() != 0){
                JSONObject userObject = (JSONObject)users.get(0);
                user = new User();
                user.setUserNick(userObject.get("name").toString());
                user.setUserImg(userObject.get("headurl").toString());
                
            }
        }

        return null;
    }

    @Override
    public boolean sendMessage(Third third, String message) throws Exception {
        return false;
    }

}
