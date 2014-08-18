package com.zhy.listen.api.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.api.ApiFeedNewsService;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.FeedNewsCount;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.service.FeedNewsService;

@Service
public class ApiFeedNewsServiceImpl implements ApiFeedNewsService {

    @Autowired
    private FeedNewsService feedNewsService;
    
    @Override
    public Response findUnreadCount(Long userId, Timestamp requestTime) {
        Response response = new Response();
        response.setErrorCode(ErrorCode.ERROR);
        try {
            int count = feedNewsService.findUnreadCount(userId, requestTime);
            response.setErrorCode(ErrorCode.SUCCESS);
            response.setResult(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response destory(FeedNews feedNews) {
        Response response = new Response();
        boolean isSuccess = feedNewsService.destory(feedNews);
        response.setErrorCode(isSuccess ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }

    @Override
    public QueryResult findUnreadList(Long userId, Timestamp requestTime) {
        List<FeedNewsCount> news  = feedNewsService.findUnreadList(userId, requestTime);
        QueryResult queryResult = new QueryResult();
        queryResult.setErrorCode(ErrorCode.SUCCESS);
        queryResult.setHitCount(news == null ? 0 : news.size());
        queryResult.setResult(news);
        return queryResult;
    }

}
