package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.dao.FriendDAO;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDAO friendDAO;

    @Override
    public List<Friend> getFriendsByUserId(Friend friend) {
        return friendDAO.getFriendsByUserId(friend);
    }

    @Override
    public boolean modifyFriendRelation(Friend friend) {
        friendDAO.updateRelation(friend);
        Friend f = new Friend(friend.getFriendId(), friend.getUserId(), System.currentTimeMillis(), friend.getIsValid());
        return friendDAO.updateRelation(f) > 0 ? true : false;
    }

    @Override
    public Friend makeFriends(Friend friend) {
        friendDAO.save(friend);

        // 互换角色
        Friend f = new Friend(friend.getFriendId(), friend.getUserId());
        return friendDAO.save(f);
    }

    @Override
    public int getUserHaveFriendCount(Long userId) {
        return friendDAO.getUserFriendSize(userId);
    }

}
