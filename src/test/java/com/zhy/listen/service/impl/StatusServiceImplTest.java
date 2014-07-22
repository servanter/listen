package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Status;
import com.zhy.listen.service.StatusService;

public class StatusServiceImplTest extends SuperTest {

    @Autowired
    private StatusService statusService;
    
    @Test
    public void testPostAndFindUserLastestStatus() {
        Status status = new Status(1L, "这是一个content", true);
        boolean isSuccess = statusService.post(status);
        Assert.assertTrue("Can't save the status.", isSuccess);
        status = statusService.findUserLastestStatus(1L);
        Assert.assertNotNull("Can't find the status by user id.", status);
    }


}
