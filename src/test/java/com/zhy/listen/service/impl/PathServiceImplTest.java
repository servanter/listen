package com.zhy.listen.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.service.PathService;

public class PathServiceImplTest extends SuperTest{

    @Autowired
    private PathService pathService;
    
    @Test
    public void testQueryByPath() {
        UserStatusPointPath path = new UserStatusPointPath();
        path.setId(1L);
        path.setLoc("40,116");
        QueryResult result = pathService.queryByPath(path, 50);
        System.out.println(result);
        Assert.assertTrue("Can't find the user from path?", result.getResult() != null && result.getResult().size() > 0);
    }
    
    @Test
    public void testSign() {
        UserStatusPointPath path = new UserStatusPointPath();
        path.setId(18L);
        path.setLoc("40,116.13");
        boolean isSuccess = pathService.sign(path);
        Assert.assertTrue("Can't sign the path ?", isSuccess);
    }

}
