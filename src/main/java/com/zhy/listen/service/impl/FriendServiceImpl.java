package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.JedisClient;
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
    private UserService userService;
    
    @Autowired
    private JedisClient jedisClient;
    
    @Autowired
    private Memcached memcached;

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FriendService#findFriendsByUserId(com.zhy.listen.entities.Friend)
     */
    @Override
    public Paging<User> findFriendsByUserId(Friend friend) {
//        List<User> myUsers = jedisClient.lrange(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId()), friend.getSinceCount(), friend.getEndPoint(), User.class);
//        if (myUsers == null) {
//            List<User> fs = friendDAO.getFriendsByUserId(friend);
//            int total = getFriendsByUserIdCount(friend);
//            Paging<User> result = new Paging<User>(total, friend.getPage(), friend.getPageSize(), fs);
//            jedisClient.rpush(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId()), fs);
//            jedisClient.jedis.set(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()), String.valueOf(total));
//            return result;
//        } else {
//            int total = 0;
//            String string = jedisClient.jedis.get(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()));
//            if (!StringUtils.isNullOrEmpty(string)) {
//                total = Integer.parseInt(string);
//            } else {
//                total = getFriendsByUserIdCount(friend);
//            }
//            return new Paging<User>(total, friend.getPage(), friend.getPageSize(), myUsers);
//        }
        List<User> result = new ArrayList<User>();
        int totalRecord = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
        List<Long> ids = jedisClient.lrange(key, friend.getSinceCount(), friend.getEndPoint());
        if (ids == null) {
            List<User> fs = friendDAO.getFriendsByUserId(friend);

            // 存放单条数据
            if (fs != null) {
                for (User f : fs) {
                    String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER, f.getId());
                    memcached.set(everyKey, f, CacheConstants.TIME_HOUR * 4);
                }

                // 存放id list
                List<Long> idList = findFriendIds(friend.getUserId());
                jedisClient.rpush(key, idList);
            }

            int total = getFriendsByUserIdCount(friend);
            return new Paging<User>(total, friend.getPage(), friend.getPageSize(), fs);
        } else {

            // 从memcached获取单条
            String keys[] = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_USER, ids.get(i));
            }
            Map<String, User> users = memcached.getMulti(keys);
            if (users == null) {
                users = new HashMap<String, User>();
            }

            // memcache中没有该数据
            if (users.size() != keys.length) {
                List<Long> needFromDBIds = new ArrayList<Long>();

                // 按照当前分页的id列表查询
                if (users.size() > 0) {
                    for (int i = 0; i < keys.length; i++) {
                        if (!users.containsKey(keys[i])) {
                            needFromDBIds.add(ids.get(i));
                        }
                    }
                } else {
                    needFromDBIds = ids;
                }
                List<User> currentUsers = userService.findUsersByIds(needFromDBIds);
                if (currentUsers != null && currentUsers.size() > 0) {
                    for (User c : currentUsers) {
                        users.put(KeyGenerator.generateKey(CacheConstants.CACHE_USER, c.getId()), c);
                        String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER, c.getId());
                        memcached.set(everyKey, c, CacheConstants.TIME_HOUR * 4);
                    }
                }
            }

            // 重新排序
            for (String eKey : keys) {
                result.add(users.get(eKey));
            }
        }

        // 获取总条数
        totalRecord = getFriendsByUserIdCount(friend);
        return new Paging<User>(totalRecord, friend.getPage(), friend.getPageSize(), result);
    }

    /**
     * 查询好友数
     * 
     * @param friend
     * @return
     */
    private int getFriendsByUserIdCount(Friend friend) {
        int count = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId());
        String value = jedisClient.get(key);
        if(value == null || value.length() == 0) {
            count = friendDAO.getFriendsByUserIdCount(friend);
            jedisClient.set(key, count);
        } else {
            count = Integer.parseInt(value);
        }
        return count;
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FriendService#removeFriendRelation(com.zhy.listen.entities.Friend)
     */
    @Override
    public boolean removeFriendRelation(Friend friend) {
        boolean isSuccess = friendDAO.updateRelation(friend) > 0;
        if(isSuccess) {
            Friend f = new Friend(friend.getFriendId(), friend.getUserId(), System.currentTimeMillis(), friend.getIsValid());
            isSuccess = friendDAO.updateRelation(f) > 0;
            if(isSuccess) {
                
                // 查询当前cache中是否有该userid
                String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());
                jedisClient.lrem(key, 1, friend.getFriendId());
                
                // 删除好友数量
                String myUserCountKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId());
                if (jedisClient.jedis.exists(myUserCountKey)) {
                    jedisClient.jedis.decr(myUserCountKey);
                }

                // 更新另外一人缓存
                String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, f.getUserId());
                jedisClient.lrem(otherKey, 1, f.getFriendId());
                
                // 删除好友数量
                String otherUserCountKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, f.getUserId());
                if (jedisClient.jedis.exists(otherUserCountKey)) {
                    jedisClient.jedis.decr(otherUserCountKey);
                }
            }
        }
        return isSuccess;
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FriendService#makeFriends(com.zhy.listen.entities.Friend)
     */
    @Override
    public boolean makeFriends(Friend friend) {
        friendDAO.save(friend);

        // 互换角色
        Friend f = new Friend(friend.getFriendId(), friend.getUserId());
        friendDAO.save(f);
        
        // 更新缓存
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getUserId());

        // 判断是否有key;如果没有,那么该用户应该是没有登录状态
        if(jedisClient.jedis.exists(key)) {
            jedisClient.lpush(key, friend.getFriendId());
            jedisClient.jedis.incr(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, friend.getUserId()));
        }
        
        // 更新另外一人缓存;如果没有,那么该用户应该是没有登录状态
        String otherKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS, friend.getFriendId());
        
        // 判断是否有key
        if(jedisClient.jedis.exists(otherKey)) {
            jedisClient.lpush(otherKey, f.getFriendId());
            jedisClient.jedis.incr(KeyGenerator.generateKey(CacheConstants.CACHE_USER_FRIENDS_COUNT, f.getUserId()));
        }
        return friend.getId() > 0 && f.getId() > 0;
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FriendService#findFriendIds(java.lang.Long)
     */
    @Override
    public List<Long> findFriendIds(Long userId) {
        return friendDAO.getFriendIds(userId);
    }

}
