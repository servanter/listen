package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.JedisClient;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.dao.FriendDAO;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.FriendService;
import com.zhy.listen.service.UserService;
import com.zhy.listen.util.StringUtils;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDAO friendDAO;

    @Autowired
    private UserService userService;
    
    @Autowired
    private JedisClient jedisClient;

    @Override
    public Paging<User> findFriendsByUserId(Friend friend) {
        List<User> myUsers = jedisClient.lrange(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId()), friend.getSinceCount(), friend.getEndPoint(), User.class);
        if (myUsers == null) {
            List<User> fs = friendDAO.getFriendsByUserId(friend);
            int total = getFriendsByUserIdCount(friend);
            Paging<User> result = new Paging<User>(total, friend.getPage(), friend.getPageSize(), fs);
            jedisClient.rpush(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId()), fs);
            jedisClient.jedis.set(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()), String.valueOf(total));
            return result;
        } else {
            int total = 0;
            String string = jedisClient.jedis.get(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()));
            if (!StringUtils.isNullOrEmpty(string)) {
                total = Integer.parseInt(string);
            } else {
                total = getFriendsByUserIdCount(friend);
            }
            return new Paging<User>(total, friend.getPage(), friend.getPageSize(), myUsers);
        }
    }

    private int getFriendsByUserIdCount(Friend friend) {
        return friendDAO.getFriendsByUserIdCount(friend);
    }

    @Override
    public boolean removeFriendRelation(Friend friend) {
        boolean isSuccess = friendDAO.updateRelation(friend) > 0;
        if(isSuccess) {
            Friend f = new Friend(friend.getFriendId(), friend.getUserId(), System.currentTimeMillis(), friend.getIsValid());
            isSuccess = friendDAO.updateRelation(f) > 0;
            if(isSuccess) {
                
                // 查询当前cache中是否有改userid
                String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
                List<User> users = jedisClient.lrange(key, 0, -1, User.class);
                if(users != null) {
                    for(User u : users) {
                        if(u.getId().longValue() == friend.getFriendId()) {
                            users.remove(u);
                            break;
                        }
                    }
                    jedisClient.jedis.del(key);
                    jedisClient.rpush(key, users);
                }
                
                // 删除好友数量
                String myUserCountKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId());
                if (jedisClient.jedis.exists(myUserCountKey)) {
                    jedisClient.jedis.decr(myUserCountKey);
                }

                // 更新另外一人缓存
                String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getFriendId());
                List<User> otherUsers = jedisClient.lrange(otherKey, 0, -1, User.class);
                if(otherUsers != null) {
                    for(User u : otherUsers) {
                        if(u.getId() == f.getFriendId()) {
                            otherUsers.remove(u);
                            break;
                        }
                    }
                    jedisClient.jedis.del(otherKey);
                    jedisClient.rpush(otherKey, otherUsers);
                }
                
                // 删除好友数量
                String otherUserCountKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getFriendId());
                if (jedisClient.jedis.exists(otherUserCountKey)) {
                    jedisClient.jedis.decr(otherUserCountKey);
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
        User user = userService.findUserById(friend.getFriendId());

        // 判断是否有key
        if(jedisClient.jedis.exists(key)) {
            jedisClient.lpush(key, user);
            jedisClient.jedis.incr(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()));
        }
        
        // 更新另外一人缓存
        String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getFriendId());
        User OtherUser = userService.findUserById(f.getFriendId());
        
        // 判断是否有key
        if(jedisClient.jedis.exists(otherKey)) {
            jedisClient.lpush(otherKey, OtherUser);
            jedisClient.jedis.incr(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, f.getFriendId()));
        }
        return friend.getId() > 0 && f.getId() > 0;
    }

    @Override
    public int findUserHaveFriendCount(Long userId) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, userId);
        String value = jedisClient.jedis.get(key);
        if(value != null && value.length() != 0) {
            return Integer.parseInt(value);
        } else {
            Friend friend = new Friend();
            friend.setUserId(userId);

            // 直接查询所有用户
            Paging<User> result = findFriendsByUserId(friend);
            return Integer.parseInt(String.valueOf(result.getTotalRecord()));
        }
    }

    @Override
    public List<Long> findFriendIds(Long userId) {
        List<Long> ids = new ArrayList<Long>();
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIEND_IDS, userId);
        List<Long> cacheIds = jedisClient.lrange(key, 0, -1, Long.class);
        if (cacheIds == null || cacheIds.isEmpty()) {
            ids = friendDAO.getFriendIds(userId);
            jedisClient.rpush(key, ids);
        } else {
            ids = cacheIds;
        }
        return ids;
    }

}
