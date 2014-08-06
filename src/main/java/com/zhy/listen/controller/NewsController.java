package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsService;
import com.zhy.listen.service.StatusService;

@Controller
@RequestMapping("/news")
public class NewsController extends CommonController {

    @Autowired
    private FeedNewsService feedNewsService;
    
    /**
     * 删除状态
     * 
     * @param status
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("destory")
    public String distroy(FeedNews feedNews, @RequestParam("type") int type, HttpServletResponse response) throws Exception {
        feedNews.setSubType(SubType.getSubFromType(type));
        Response response2 = feedNewsService.destory(feedNews);
        print(response2, response);
        return null;
    }
}
