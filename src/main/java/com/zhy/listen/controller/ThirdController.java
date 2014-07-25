package com.zhy.listen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.third.DouBan;
import com.zhy.listen.bean.third.KaiXin;
import com.zhy.listen.bean.third.QQ;
import com.zhy.listen.bean.third.QQWeiBo;
import com.zhy.listen.bean.third.RenRen;
import com.zhy.listen.bean.third.Sina;
import com.zhy.listen.bean.third.Sohu;
import com.zhy.listen.bean.third.WangYi;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.third.DispatchAuthorizeService;

/**
 * 第三方授权控制器
 * 
 * @author zhy19890221@gmail.com
 * 
 */
@Controller
@RequestMapping(value = "/third")
public class ThirdController {

    @Autowired
    private DispatchAuthorizeService dispatchAuthorizeService;

    /**
     * 授权第三�?
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "authorize/{srcName}")
    public String authorize(HttpServletRequest request, HttpServletResponse response, @PathVariable("srcName") String srcName) {
        Src src = Src.valueOf(srcName);
        String url = null;

        // 腾讯的需要request 特殊处理
        if (src.equals(Src.QQ)) {
            try {
                url = new Oauth().getAuthorizeURL(request);
            } catch (QQConnectException e) {
                e.printStackTrace();
            }
        }
        if (url == null || url.length() == 0) {
            try {
                url = dispatchAuthorizeService.authorize(src);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.getSession().setAttribute("src", src);
        return "redirect:" + url;
    }

    /**
     * 回调处理
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("callback")
    public String callback(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code, @RequestParam("openid") String openId, @RequestParam("openkey") String openKey) {
        HttpSession session = request.getSession();
        Src src = (Src) session.getAttribute("src");
        Third third = new Third().thirdFactory(src);
        switch (src) {
        case SINA:
            ((Sina) third).setCode(code);
            break;

        case QQWEIBO:
            ((QQWeiBo) third).setCode(code);
            ((QQWeiBo) third).setOpenId(openId);
            ((QQWeiBo) third).setOpenKey(openKey);
            break;

        case RENREN:
            ((RenRen) third).setCode(code);
            break;
        case KAIXIN:
            ((KaiXin) third).setCode(code);
            break;
        case DOUBAN:
            ((DouBan) third).setCode(code);
            break;
        case WANGYI:
            ((WangYi) third).setCode(code);
            break;
        case QQ:
            ((QQ) third).setRequest(request);
            break;
        case SOHU:
            ((Sohu)third).setCode(code);
        }
        User user = null;
        try {
            user = dispatchAuthorizeService.getUserFromThird(third);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 此时user/third都没有存�?
        session.setAttribute("third", third);
        session.setAttribute("user", user);
        return "/user/register.jsp";
    }

    /**
     * 发�?�消息邀请好�?
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("thirdSend")
    public String thirdSend(HttpServletRequest request, HttpServletResponse response) {
        Third third = (Third) request.getSession().getAttribute("third");
        Sina sina = (Sina) third;
        sina.setToken(sina.getToken());
        try {
            dispatchAuthorizeService.invateUsers(sina, "好人,我是好人", 5L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
