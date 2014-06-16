package com.zhy.listen.third.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;
import com.zhy.listen.bean.User;
import com.zhy.listen.common.Constant;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.third.AuthorizeService;
import com.zhy.listen.third.DispatchAuthorizeService;

@Service("dispatchAuthorizeService")
public class DispatchAuthorizeServiceImpl implements DispatchAuthorizeService {

    @Autowired
    private Map<String, AuthorizeService> thirdServices;

    @Autowired
    private TemplateService templateService;

    @Override
    public String authorize(Src src) throws Exception {
        if (thirdServices.containsKey(src.getServiceName())) {
            return thirdServices.get(src.getServiceName()).authorize();
        }
        return null;
    }

    @Override
    public User getUserFromThird(Third third) throws Exception {
        if (thirdServices.containsKey(third.getSrc().getServiceName())) {
            return thirdServices.get(third.getSrc().getServiceName()).getUserFromThird(third);
        }
        return null;
    }

    @Override
    public boolean invateUsers(Third third, String userNames, Long infoId) throws Exception {
        if (thirdServices.containsKey(third.getSrc().getServiceName())) {
            String url = templateService.getMessage(Constant.TEMPLATE_THIRD_INFODETAIL_WORDS, infoId.toString());
            StringBuilder packageUserNames = new StringBuilder();
            for (String userName : userNames.split(",")) {
                packageUserNames.append(templateService.getMessage(Constant.TEMPLATE_THIRD_NOTICE_USERPRE_WORDS, userName));
            }
            String message = templateService.getMessage(Constant.TEMPLATE_THIRD_MESSAGE_WORDS) + url + packageUserNames.toString();
            return thirdServices.get(third.getSrc().getServiceName()).sendMessage(third, message);
        }
        return false;
    }

}
