package com.zhy.listen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.api.ApiUserService;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.User;
import com.zhy.listen.util.FileUpload;

/**
 * 用户控制层
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController extends CommonController {

    @Autowired
    private ApiUserService apiUserService;

    @Autowired
    private FileUpload fileUpload;

    @RequestMapping("modifyHead")
    public String modifyHead(HttpServletRequest request, HttpServletResponse response, @RequestParam("user_id") Long userId) throws Exception {
        String fileName = fileUpload.uploadHead(request, response);
        if (fileName != null) {
            User user = new User();
            user.setId(userId);
            user.setUserImg(fileName);
            Response resp = apiUserService.modifyUserInfo(user);
            resp.setResult(fileUpload.getCurrentHeadWebUrlPre() + fileName);
            print(resp, response);
        } else {
            Response resp = new Response();
            resp.setErrorCode(ErrorCode.ERROR);
            print(resp, response);
        }
        return null;
    }

    @RequestMapping("modifyInfo")
    public String modifyInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("user_id") Long userId, @RequestParam(required = false, value = "sex") Integer sex,
            @RequestParam(value = "user_nick", required = false) String userNick, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone) throws Exception {
        User user = new User(userId);
        user.setEmail(email);
        user.setMobile(phone);
        user.setSex(sex);
        user.setUserNick(userNick);
        Response resp = apiUserService.modifyUserInfo(user);
        print(resp, response);
        return null;
    }
}
