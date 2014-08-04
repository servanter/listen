package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.dao.FriendDAO;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDAO friendDAO;

    @Override
    public Paging<Friend> findFriendsByUserId(Page page) {
        List<Friend> friends = friendDAO.getFriendsByUserId(page);
        int total = getFriendsByUserIdCount(page);
        return new Paging<Friend>(total, page.getPage(), page.getPageSize(), friends);
    }

    public int getFriendsByUserIdCount(Page page) {
        return friendDAO.getFriendsByUserIdCount(page);
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
    public int findUserHaveFriendCount(Long userId) {
        return friendDAO.getUserFriendSize(userId);
    }

    @Override
    public List<Long> findFriendIds(Long userId) {
        return friendDAO.getFriendIds(userId);
    }

}
