package com.zhy.listen.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.StatusService;

public class StatusServiceImplTest extends SuperTest {

    @Autowired
    private StatusService statusService;
    
    @Test
    public void testPostAndFindUserLastestStatus() {
        Status status = new Status(1L, "这是一个content", true);
        Response response = statusService.post(status);
        Assert.assertTrue("Can't save the status.", response.getErrorCode() == ErrorCode.SUCCESS);
        status = statusService.findUserLastestStatus(1L);
        Assert.assertNotNull("Can't find the status by user id.", status);
    }


}
