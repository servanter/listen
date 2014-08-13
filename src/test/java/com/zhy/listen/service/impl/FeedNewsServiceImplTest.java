package com.zhy.listen.service.impl;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.FeedNewsCount;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsService;
import com.zhy.listen.service.OnlineService;

public class FeedNewsServiceImplTest extends SuperTest{

    @Autowired
    private FeedNewsService feedNewsService;
    
    @Autowired
    private OnlineService onlineService;
    
    @Test
    public void testFindDetailByIdAndType() {
        Status status = feedNewsService.findDetailByIdAndType(1L, SubType.STATUS);
        Assert.assertNotNull("Can't find the detail ?", status);
    }
    
    @Test
    public void testAdd() {
        List<Long> iii = new ArrayList<Long>();
        iii.add(2L);
        onlineService.login(iii);
        
        Status status = new Status();
        status.setId(2L);
        status.setUserId(1L);
        status.setContent("这是");
        feedNewsService.push(status, SubType.STATUS);
        
        int count = feedNewsService.findUnreadCount(2L, new Timestamp(System.currentTimeMillis()));
        System.out.println(count);
        
//        List<FeedNews> list = feedNewsService.findUnreadList(2L, new Timestamp(System.currentTimeMillis()));
//        System.out.println(list);
//        
//        count = feedNewsService.findUnreadCount(2L, new Timestamp(System.currentTimeMillis()));
//        System.out.println(count);
        
        FeedNews feedNews = new FeedNews();
        feedNews.setId(81L);
        feedNews.setSubType(SubType.STATUS);
        feedNewsService.destory(feedNews);
//        
        count = feedNewsService.findUnreadCount(2L, new Timestamp(System.currentTimeMillis()));
        System.out.println(count);
//        
//        List<FeedNews> list = feedNewsService.findUnreadList(2L, new Timestamp(System.currentTimeMillis()));
//        System.out.println(list);
    }

    @Test
    public void testFindBaseFeedsByUserId() {
        FeedNews feedNews = new FeedNews();
        feedNews.setUserId(1L);
        feedNews.setPageSize(5);
        feedNews.setPage(1);
        System.out.println(feedNewsService.findBaseFeedsByUserId(feedNews));;
    }
    
    @Test
    public void testFindByNews() {
        FeedNews feedNews = new FeedNews();
        feedNews.setUserId(1L);
        feedNews.setPageSize(8);
        feedNews.setPage(1);
        Paging<FeedNewsCount> findByNews = feedNewsService.findByNews(feedNews);
        System.out.println(findByNews.getResult().size() + "+++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}
