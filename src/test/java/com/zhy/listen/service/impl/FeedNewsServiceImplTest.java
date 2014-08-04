package com.zhy.listen.service.impl;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsService;

public class FeedNewsServiceImplTest extends SuperTest{

    @Autowired
    private FeedNewsService feedNewsService;
    
    @Test
    public void testFindByNews() {
    }

    @Test
    public void testFindDetailByIdAndType() {
        Status status = feedNewsService.findDetailByIdAndType(1L, SubType.STATUS);
        Assert.assertNotNull("Can't find the detail ?", status);
    }
    
    @Test
    public void testAdd() {
        Status status = new Status();
        status.setId(22L);
        status.setUserId(1L);
        status.setContent("这是");
        feedNewsService.push(status, SubType.STATUS);
    }

}
