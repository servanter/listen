package com.zhy.listen.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.service.FriendService;

public class FriendServiceImplTest extends SuperTest{

    @Autowired
    private FriendService friendService;
    
    @Test
    public void testFindFriendsByUserId() {
        Friend friend = new Friend();
        friend.setUserId(17L);
        Paging<UserStatusPointPath> result = friendService.findFriendsByUserId(friend);
        System.out.println(result);
    }

    @Test
    public void testModifyFriendRelation() {
        Friend friend = new Friend();
        friend.setUserId(17L);
        friend.setFriendId(13L);
        friend.setIsValid(false);
        Assert.assertTrue("Can't remove the friendship.", friendService.removeFriendRelation(friend));
    }

    @Test
    public void testMakeFriends() {
        Friend friend = new Friend();
        friend.setUserId(17L);
        friend.setFriendId(4L);
        boolean isSuccess = friendService.makeFriends(friend);
        Assert.assertTrue("Can't make friends?", isSuccess);
    }

    @Test
    public void testFindFriendIds() {
        List<Long> ids = friendService.findFriendIds(1L);
        Assert.assertNotNull("Can't find the friends ids?" , ids);
    }

}
