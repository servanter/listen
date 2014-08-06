package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.UserService;

/**
 * 用户控制层
 * 
 * @author zhanghongyan@outlook.com
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends CommonController{

    @Autowired
    private UserService userService;
    
    @RequestMapping("login")
    public String login(User user, HttpServletResponse response) {
        Response resp = userService.login(user);
        print(resp, response);
        return null;
    }
}
