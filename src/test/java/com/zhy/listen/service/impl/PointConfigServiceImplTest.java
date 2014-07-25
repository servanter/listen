package com.zhy.listen.service.impl;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.service.PointConfigService;

public class PointConfigServiceImplTest extends SuperTest {

    @Autowired
    private PointConfigService pointConfigService;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        pointConfigService = null;
    }

    @Test
    public void testGetPointConfigByPoint() {
        Assert.assertNotNull("Can't find the config.Maybe the init sql is wrong.", pointConfigService.findPointConfigByPoint(100L));
    }

}
