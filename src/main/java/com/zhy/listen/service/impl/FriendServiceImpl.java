package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.dao.FriendDAO;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.FriendService;
import com.zhy.listen.service.UserService;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDAO friendDAO;

    @Autowired
    private Memcached memcached;
    
    @Autowired
    private UserService userService;

    @Override
    public Paging<User> findFriendsByUserId(Friend friend) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
        Paging<User> friends = memcached.get(key);
        if (friends != null) {
            return friends;
        } else {
            List<User> fs = friendDAO.getFriendsByUserId(friend);
            int total = getFriendsByUserIdCount(friend);
            Paging<User> result = new Paging<User>(total, friend.getPage(), friend.getPageSize(), fs);
            memcached.set(key, result, CacheConstants.TIME_HOUR * 4);
            return result;
        }
    }

    private int getFriendsByUserIdCount(Friend friend) {
        return friendDAO.getFriendsByUserIdCount(friend);
    }

    @Override
    public boolean modifyFriendRelation(Friend friend) {
        boolean isSuccess = friendDAO.updateRelation(friend) > 0;
        if(isSuccess) {
            Friend f = new Friend(friend.getFriendId(), friend.getUserId(), System.currentTimeMillis(), friend.getIsValid());
            isSuccess = friendDAO.updateRelation(f) > 0;
            if(isSuccess) {
                
                // 更新缓存
                String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
                Paging<User> users = memcached.get(key);
                if(users != null) {
                    for(User u : users.getResult()) {
                        if(u.getId().longValue() == friend.getFriendId()) {
                            users.getResult().remove(u);
                            break;
                        }
                    }
                    users.setTotalRecord(users.getTotalRecord() - 1);
                    memcached.set(key, users, CacheConstants.TIME_HOUR * 4);
                }
                
                // 更新另外一人缓存
                String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getFriendId());
                Paging<User> otherUsers = memcached.get(otherKey);
                if(otherUsers != null) {
                    for(User u : otherUsers.getResult()) {
                        if(u.getId() == f.getFriendId()) {
                            otherUsers.getResult().remove(u);
                            break;
                        }
                    }
                    otherUsers.setTotalRecord(otherUsers.getTotalRecord() - 1);
                    memcached.set(otherKey, otherUsers, CacheConstants.TIME_HOUR * 4);
                }
            }
        }
        return isSuccess;
    }

    @Override
    public boolean makeFriends(Friend friend) {
        friendDAO.save(friend);

        // 互换角色
        Friend f = new Friend(friend.getFriendId(), friend.getUserId());
        friendDAO.save(f);
        
        // 更新缓存
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
        Paging<User> users = memcached.get(key);
        if(users != null) {
            List<User> cacheUsers = new ArrayList<User>();
            User user = userService.findUserById(friend.getFriendId());
            cacheUsers.add(user);
            for(User u : users.getResult()) {
                cacheUsers.add(u);
            }
            users.setResult(cacheUsers);
            users.setTotalRecord(users.getTotalRecord() + 1);
            memcached.set(key, users, CacheConstants.TIME_HOUR * 4);
        }
        
        // 更新另外一人缓存
        String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getFriendId());
        Paging<User> otherUsers = memcached.get(otherKey);
        if(otherUsers != null) {
            List<User> cacheUsers = new ArrayList<User>();
            User user = userService.findUserById(f.getFriendId());
            cacheUsers.add(user);
            for(User u : otherUsers.getResult()) {
                cacheUsers.add(u);
            }
            otherUsers.setResult(cacheUsers);
            otherUsers.setTotalRecord(otherUsers.getTotalRecord() + 1);
            memcached.set(otherKey, otherUsers, CacheConstants.TIME_HOUR * 4);
        }
        return friend.getId() > 0 && f.getId() > 0;
    }

    @Override
    public int findUserHaveFriendCount(Long userId) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, userId);
        Paging<User> users = memcached.get(key);
        if (users != null && users.getTotalRecord() > 0) {
            return new Long(users.getTotalRecord()).intValue();
        } else {
            Friend friend = new Friend();
            friend.setUserId(userId);

            // 直接查询所有用户
            Paging<User> result = findFriendsByUserId(friend);
            memcached.set(key, result, CacheConstants.TIME_HOUR * 4);
            return new Long(result.getTotalRecord()).intValue();
        }
    }

    @Override
    public List<Long> findFriendIds(Long userId) {
        List<Long> ids = new ArrayList<Long>();
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, userId);
        Paging<User> users = memcached.get(key);
        if (users != null && users.getResult() != null) {
            for (User user : users.getResult()) {
                ids.add(user.getId());
            }
        } else {
            Friend friend = new Friend();
            friend.setUserId(userId);

            // 直接查询所有用户
            Paging<User> result = findFriendsByUserId(friend);
            for (User user : result.getResult()) {
                ids.add(user.getId());
            }
            memcached.set(key, result, CacheConstants.TIME_HOUR * 4);
        }
        return ids;
    }

}
