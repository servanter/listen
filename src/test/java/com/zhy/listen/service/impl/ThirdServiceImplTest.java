package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Src;
import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.ThirdService;
import com.zhy.listen.service.UserService;

public class ThirdServiceImplTest extends SuperTest {

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        thirdService = null;
    }

    @Test
    public void testAddAndGet() {
        Long userId = userService.register(new User("asdadadadasxcvxv", "q3r23rsdfwertg"));
        Third third = new Third();
        third.setSrc(Src.KAIXIN);
        third.setUserId(userId);
        third.setEndTime(new Timestamp(System.currentTimeMillis() + 454545L));
        third.setMetaIndex3("asdhsdfksdjkghuirhgksjdhn");
        third = thirdService.add(third);
        List<Third> thirds = thirdService.getThirdsById(userId);
        Assert.assertNotNull("Can't save the third data", third.getId());
        Assert.assertNotNull("Can't find the third data", thirds);
    }

}
