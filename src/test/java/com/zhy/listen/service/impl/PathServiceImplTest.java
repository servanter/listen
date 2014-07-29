package com.zhy.listen.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.entities.Path;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.PathService;

public class PathServiceImplTest extends SuperTest{

    @Autowired
    private PathService pathService;
    
    @Test
    public void testQueryByPath() {
        Path path = new Path();
        path.setLoc("39.914889,116");
        List<User> result = pathService.queryByPath(path, 50);
        System.out.println(result);
        Assert.assertTrue("Can't find the user from path?", result != null && result.size() > 0);
    }

}
