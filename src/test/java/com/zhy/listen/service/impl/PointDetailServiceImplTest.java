package com.zhy.listen.service.impl;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.PointDetail;
import com.zhy.listen.bean.PointType;
import com.zhy.listen.service.PointDetailService;

public class PointDetailServiceImplTest extends SuperTest {

    @Autowired
    private PointDetailService pointDetailService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        pointDetailService = null;
    }

    @Test
    public void testAdd() {
        PointDetail pointDetail = new PointDetail();
        pointDetail.setUserId(1L);
        pointDetail.setPointType(PointType.LOGIN);
        Assert.assertTrue("Can't save the point detail.", pointDetailService.add(pointDetail));
    }

}
