package com.zhy.listen.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.api.ApiUserService;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.UserService;

@Service
public class ApiUserServiceImpl implements ApiUserService {

    @Autowired
    private UserService userService;

    @Override
    public Response modifyUserInfo(User user) {
        Response response = new Response();
        boolean isSuccess = userService.completeInfo(user);
        response.setErrorCode(isSuccess ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }

}
