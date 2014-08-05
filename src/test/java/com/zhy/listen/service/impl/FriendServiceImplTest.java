package com.zhy.listen.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AssertThrows;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.FriendService;

public class FriendServiceImplTest extends SuperTest{

    @Autowired
    private FriendService friendService;
    
    @Test
    public void testFindFriendsByUserId() {
        Friend friend = new Friend();
        friend.setUserId(1L);
        Paging<User> result = friendService.findFriendsByUserId(friend);
        System.out.println(result);
    }

    @Test
    public void testModifyFriendRelation() {
        Friend friend = new Friend();
        friend.setUserId(1L);
        friend.setFriendId(3L);
        friend.setIsValid(false);
        Assert.assertTrue("Can't remove the friendship.", friendService.modifyFriendRelation(friend));
    }

    @Test
    public void testMakeFriends() {
        Friend friend = new Friend();
        friend.setUserId(1L);
        friend.setFriendId(4L);
        boolean isSuccess = friendService.makeFriends(friend);
        Assert.assertTrue("Can't make friends?", isSuccess);
    }

    @Test
    public void testFindUserHaveFriendCount() {
        int total = friendService.findUserHaveFriendCount(1L);
        Assert.assertTrue("Can't find the friend?", total > 0);
    }

    @Test
    public void testFindFriendIds() {
        List<Long> ids = friendService.findFriendIds(1L);
        Assert.assertNotNull("Can't find the friends ids?" , ids);
    }

}
