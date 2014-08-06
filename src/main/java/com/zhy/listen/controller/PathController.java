package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.service.PathService;

@Controller
@RequestMapping("/path")
public class PathController {

    @Autowired
    private PathService pathService;
    
    @RequestMapping(value="nearby", method = RequestMethod.POST)
    public String searchResult(UserStatusPointPath path, @RequestParam("mile") Integer mile, HttpServletResponse response) throws Exception {
        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        QueryResult queryResult = pathService.queryByPath(path, mile);
        JSONObject jsonObject = JSONObject.fromObject(queryResult);
        response.getWriter().print(jsonObject);
        return null;
    }
    
//    @InitBinder("path")
//    public void initBinderToPath(WebDataBinder binder) {
//        binder.setFieldDefaultPrefix("path.");
//    }
    
    @RequestMapping(value="pathSetting", method = RequestMethod.POST)
    public String cleanPath(UserStatusPointPath path, HttpServletResponse response) throws Exception {
        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        Response response2 = pathService.pathSetting(path);
        JSONObject jsonObject = JSONObject.fromObject(response2);
        response.getWriter().print(jsonObject);
        return null;
    }
}
