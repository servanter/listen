package com.zhy.listen.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.api.ApiFeedNewsService;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsService;
import com.zhy.listen.service.StatusService;

@Controller
@RequestMapping("/news")
public class NewsController extends CommonController {

    @Autowired
    private ApiFeedNewsService apiFeedNewsService;

    /**
     * 删除新鲜事
     * 
     * @param status
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("destory")
    public String distroy(FeedNews feedNews, @RequestParam("type") int type, HttpServletResponse response) throws Exception {
        feedNews.setSubType(SubType.getSubFromType(type));
        Response resp = apiFeedNewsService.destory(feedNews);
        print(resp, response);
        return null;
    }

    /**
     * 未读条数
     * 
     * @param userId
     * @param time
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("unreadCount")
    public String getUnReadCount(@RequestParam("user_id") Long userId, @RequestParam("request_time") long time, HttpServletResponse response)
            throws Exception {
        Response resp = apiFeedNewsService.findUnreadCount(userId, new Timestamp(time));
        print(resp, response);
        return null;
    }

    /**
     * 未读列表
     * 
     * @param userId
     * @param time
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("unreadList")
    public String getUnreadList(@RequestParam("user_id") Long userId, @RequestParam("request_time") long time, HttpServletResponse response)
            throws Exception {
        QueryResult queryResult = apiFeedNewsService.findUnreadList(userId, new Timestamp(time));
        print(queryResult, response);
        return null;
    }
}
