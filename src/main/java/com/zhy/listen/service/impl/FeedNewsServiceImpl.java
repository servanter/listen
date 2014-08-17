package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.CommentType;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.bean.cache.CacheNewFeed;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.JedisClient;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.dao.FeedNewsDAO;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.service.CommentService;
import com.zhy.listen.service.FeedNewsFactory;
import com.zhy.listen.service.FeedNewsService;
import com.zhy.listen.service.FriendService;
import com.zhy.listen.service.OnlineService;
import com.zhy.listen.service.UserService;

@Service
public class FeedNewsServiceImpl implements FeedNewsService {

    @Autowired
    private FeedNewsDAO feedNewsDAO;

    @Autowired
    private FeedNewsFactory feedNewsFactory;

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private FriendService friendService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private Memcached memcached;
    
    @Autowired
    private JedisClient jedisClient;

    private List<FeedNews> findByNewsFromDB(FeedNews feedNews) {
        return feedNewsDAO.getByNews(feedNews);
    }
    
    private int findByNewsFromDBCount(FeedNews feedNews) {
        String totalKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS_COUNT, feedNews.getUserId());
        String total = jedisClient.jedis.get(totalKey);
        int totalRecord = 0;
        if(total == null || total.length() == 0) {
            totalRecord =  feedNewsDAO.getByNewsCount(feedNews);
            jedisClient.jedis.set(totalKey, String.valueOf(totalRecord));
        } else {
            totalRecord = Integer.parseInt(total);
        }
        return totalRecord;
    }

    @Override
    public <T extends Object> T findDetailByIdAndType(Long id, SubType type) {
        return (T) feedNewsFactory.generateService(type).findById(id);
    }

    @Override
    public boolean push(Page page, SubType type) {
        try {

            FeedNews feedNews = feedNewsFactory.generateFeedNews(page, type);
            int affect = feedNewsDAO.save(feedNews);

            // 更新用户索引，定时重新建立
            if (type == SubType.STATUS) {
                userService.modifyIsIndex(feedNews.getUserId(), false);
            }
            if (affect > 0) {
                
                // 重新查询
                FeedNews f = feedNewsDAO.getById(feedNews.getId());

                // 放入新鲜事池
                memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);

                // 放入个人新鲜事池
                String profileNewsKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS, feedNews.getUserId());
                jedisClient.lpush(profileNewsKey, f);

                // 这里应该用消息队列做
                List<Long> ids = friendService.findFriendIds(f.getUserId());
                if (ids != null && ids.size() > 0) {
                    List<Long> onlineUserIds = onlineService.findAllOnlineUserIds();
                    if (onlineUserIds != null && onlineUserIds.size() > 0) {

                        // 我的好友中在线列表
                        List<Long> onlineMyUserIds = new ArrayList<Long>();
                        for (Long id : ids) {
                            if (onlineUserIds.contains(id)) {
                                onlineMyUserIds.add(id);
                            }
                        }
                        // push feed news
                        onlineService.pushUsers(onlineMyUserIds, new Timestamp(System.currentTimeMillis()), f);
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<FeedNews> findUnreadList(Long userId, Timestamp requestTime) {
        List<FeedNews> result = new ArrayList<FeedNews>();
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        List<CacheNewFeed> newFeeds = jedisClient.lrange(key, 0, -1, CacheNewFeed.class);

        // 从缓存中取新鲜事
        if (newFeeds != null && newFeeds.size() > 0) {
            String[] keys = new String[newFeeds.size()];
            for (int i = 0; i < newFeeds.size(); i++) {
                keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, newFeeds.get(i).getNewId());
            }
            List<Long> needFromDB = new ArrayList<Long>();
            Map<String, FeedNews> feedNews = memcached.getMulti(keys);
            result.addAll(feedNews.values());
            for (String k : keys) {
                if (!feedNews.containsKey(k)) {
                    needFromDB.add(Long.parseLong(KeyGenerator.getRowObject(k, CacheConstants.CACHE_FEED_NEWS)));
                }
            }

            // 从数据库中查询
            if (needFromDB.size() > 0) {
                List<FeedNews> list = findByIds(needFromDB);
                result.addAll(list);
            }
        }
        jedisClient.jedis.del(key);
        return result;
    }

    @Override
    public int findUnreadCount(Long userId, Timestamp requestTime) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        return jedisClient.jedis.llen(key).intValue();
    }

    @Override
    public List<FeedNews> findByIds(List<Long> ids) {
        List<FeedNews> news = feedNewsDAO.getByIds(ids);
        
        // 设置缓存
        for (FeedNews f : news) {
            memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);
        }
        return news;
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FeedNewsService#findBaseFeedsByUserId(java.lang.Long)
     */
    @Override
    public List<FeedNews> findBaseFeedsByUserId(FeedNews feedNews) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS, feedNews.getUserId());
        List<FeedNews> result = jedisClient.lrange(key, feedNews.getSinceCount(), feedNews.getEndPoint(), FeedNews.class);
        
        // TODO 是否size不足,需要重新查询?如果确实小于size?
//        if(result == null || result.isEmpty() || result.size() < feedNews.getPageSize()) {
        if(result == null || result.isEmpty()) {
            Paging<FeedNews> list = findByNews(feedNews);
            if(list != null && !list.getResult().isEmpty()){
                findByNewsFromDBCount(feedNews);
                return list.getResult().size() >= 5 ? list.getResult().subList(0, 5) : list.getResult().subList(0, list.getResult().size());
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FeedNewsService#findByNews(com.zhy.listen.entities.FeedNews)
     */
    @Override
    public Paging<FeedNews> findByNews(FeedNews feedNews) {

        Paging<FeedNews> result = new Paging<FeedNews>();
        // 构造方法中有计算sinceCount
        int startIndex = feedNews.getSinceCount();
        int endIndex = startIndex + feedNews.getPageSize();
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS, feedNews.getUserId());
        List<FeedNews> list = jedisClient.lrange(key, feedNews.getSinceCount(), feedNews.getEndPoint(), FeedNews.class);
        if (list != null && !list.isEmpty() && list.size() == feedNews.getPageSize()) {
            int totalRecord = findByNewsFromDBCount(feedNews);
            result = new Paging<FeedNews>(totalRecord, feedNews.getPage(), feedNews.getPageSize(), list);
        } else {

            // 不够条数或者缓存直接为null
            int fetchSize = feedNews.getPageSize();
            int sinceCount = 0;
            if (list != null) {
                fetchSize = endIndex - list.size();
                sinceCount = list.size();
            } else {
                fetchSize = endIndex;
            }
            FeedNews feedNews2 = new FeedNews();
            feedNews2.setPageSize(fetchSize);
            feedNews2.setUserId(feedNews.getUserId());
            feedNews2.setSinceCount(sinceCount);
            List<FeedNews> currentResult = findByNewsFromDB(feedNews2);
            if (currentResult != null && !currentResult.isEmpty()) {
                int totalRecord = findByNewsFromDBCount(feedNews2);
                
                // 追加
                jedisClient.lrem(key, 0, currentResult);
                jedisClient.rpush(key, currentResult);
                if(list != null && list.size() > 0) {
                    list.addAll(currentResult);
                } else {
                    list = currentResult;
                }
                result = new Paging<FeedNews>(totalRecord, feedNews.getPage(), feedNews.getPageSize(), list);
            } else {
                
                // 没有查询到，那么缓存中有该页的所有数据
                if(list != null) {
                    if(list.size() > startIndex) {
                        List<FeedNews> subList = list.subList(startIndex, list.size());
                        int total = findByNewsFromDBCount(feedNews);
                        result = new Paging<FeedNews>(total, feedNews.getPage(), feedNews.getPageSize(), subList);
                    }
                }
            }
        }
        if(result != null && !result.getResult().isEmpty()) {
            List<Long> ids = new ArrayList<Long>();
            for(FeedNews news : result.getResult()) {
                ids.add(news.getId());
            }
            Map<Long, Integer> commentCounts = commentService.findCommentsCountsByIds(CommentType.FEEDNEWS, ids);
            
        }
        return result;
    }

    @Override
    public boolean destory(FeedNews feedNews) {
        FeedNews news = feedNewsDAO.getById(feedNews.getId());
        int affect = feedNewsDAO.delete(feedNews);
        if(affect > 0) {
            Page p = feedNewsFactory.generateSub(news);
            affect = feedNewsFactory.generateService(feedNews.getSubType()).remove(p);
            Response response = new Response();
            response.setErrorCode(affect > 0 ? ErrorCode.SUCCESS : ErrorCode.ERROR);
            if(affect > 0 && feedNews.getSubType() == SubType.STATUS) {
                
                // 更新用户索引，定时重新建立
                userService.modifyIsIndex(news.getUserId(), false);
                
            }
            // 这里应该用消息队列做
            List<Long> ids = friendService.findFriendIds(news.getUserId());
            if (ids != null && ids.size() > 0) {
                List<Long> onlineUserIds = onlineService.findAllOnlineUserIds();
                if (onlineUserIds != null && onlineUserIds.size() > 0) {

                    // 我的好友中在线列表
                    List<Long> onlineMyUserIds = new ArrayList<Long>();
                    for (Long id : ids) {
                        if (onlineUserIds.contains(id)) {
                            onlineMyUserIds.add(id);
                        }
                    }
                    // remove feed news
                    onlineService.removeUsers(onlineMyUserIds, new Timestamp(System.currentTimeMillis()), news);
                }
            }
            return true;
        }
        return false;
    }

}
