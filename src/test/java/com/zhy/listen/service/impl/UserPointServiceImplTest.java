package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.entities.UserPoint;
import com.zhy.listen.service.UserPointService;

public class UserPointServiceImplTest extends SuperTest{

    @Autowired
    private UserPointService userPointService;
    
    @Test
    public void testAddAndFind() {
//        UserPoint userPoint = new UserPoint();
//        userPoint.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        userPoint.setUserHonour("等级一");
//        userPoint.setPoint(100L);
//        userPoint.setUserId(1L);
//        boolean isSuccess = userPointService.add(userPoint);
//        Assert.assertTrue("Can't save the user point ?", isSuccess);
        
        UserPoint point = userPointService.findByUserId(1L);
        Assert.assertTrue("Can't find the user point ?", point != null);
    }

}
