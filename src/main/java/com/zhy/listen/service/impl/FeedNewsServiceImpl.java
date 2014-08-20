package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.FeedNewsCount;
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
import com.zhy.listen.util.BeanUtils;

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
        if (total == null || total.length() == 0) {
            totalRecord = feedNewsDAO.getByNewsCount(feedNews);
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
                jedisClient.lpush(profileNewsKey, f.getId());

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

    /*
     * (non-Javadoc)
     * 
     * @see com.zhy.listen.service.FeedNewsService#findUnreadList(java.lang.Long, java.sql.Timestamp)
     */
    @Override
    public List<FeedNewsCount> findUnreadList(Long userId, Timestamp requestTime) {
        List<FeedNews> feedList = new ArrayList<FeedNews>();
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
            feedList.addAll(feedNews.values());
            for (String k : keys) {
                if (!feedNews.containsKey(k)) {
                    needFromDB.add(Long.parseLong(KeyGenerator.getRowObject(k, CacheConstants.CACHE_FEED_NEWS)));
                }
            }

            // 从数据库中查询
            if (needFromDB.size() > 0) {
                List<FeedNews> list = findByIds(needFromDB);
                feedList.addAll(list);
            }
        }
        jedisClient.jedis.del(key);
        return initFeedNewsComment(feedList);
    }

    @Override
    public int findUnreadCount(Long userId, Timestamp requestTime) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        return jedisClient.jedis.llen(key).intValue();
    }

    private List<FeedNews> findByIds(List<Long> ids) {
        List<FeedNews> news = feedNewsDAO.getByIds(ids);

        // 设置缓存
        for (FeedNews f : news) {
            memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);
        }
        return news;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zhy.listen.service.FeedNewsService#findByNews(com.zhy.listen.entities.FeedNews)
     */
    @Override
    public Paging<FeedNewsCount> findByNews(FeedNews feedNews) {
        List<FeedNews> result = new ArrayList<FeedNews>();

        // 初始化评论数
        List<FeedNewsCount> listCount = new ArrayList<FeedNewsCount>();
        int totalRecord = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS, feedNews.getUserId());
        List<Long> ids = jedisClient.lrange(key, feedNews.getSinceCount(), feedNews.getEndPoint());
        if (ids == null) {
            result = findByNewsFromDB(feedNews);

            // 存放单条数据
            if (result != null) {
                for (FeedNews f : result) {
                    String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId());
                    memcached.set(everyKey, f, CacheConstants.TIME_HOUR * 4);
                }

                // 存放id list
                List<Long> idList = feedNewsDAO.getIdsByNews(feedNews);
                jedisClient.rpush(key, idList);
            }
        } else {

            // 从memcached获取单条
            String keys[] = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, ids.get(i));
            }
            Map<String, FeedNews> cacheFeedNews = memcached.getMulti(keys);
            if (cacheFeedNews == null) {
                cacheFeedNews = new HashMap<String, FeedNews>();
            }

            // memcache中没有该数据
            if (cacheFeedNews.size() != keys.length) {
                List<Long> needFromDBIds = new ArrayList<Long>();

                // 按照当前分页的id列表查询
                if (cacheFeedNews.size() > 0) {
                    for (int i = 0; i < keys.length; i++) {
                        if (!cacheFeedNews.containsKey(keys[i])) {
                            needFromDBIds.add(ids.get(i));
                        }
                    }
                } else {
                    needFromDBIds = ids;
                }
                List<FeedNews> currentFeedNews = feedNewsDAO.getByIds(needFromDBIds);
                if (currentFeedNews != null && currentFeedNews.size() > 0) {
                    for (FeedNews f : currentFeedNews) {
                        cacheFeedNews.put(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f);
                        String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId());
                        memcached.set(everyKey, f, CacheConstants.TIME_HOUR * 4);
                    }
                }

                // 重新排序
                for (String eKey : keys) {
                    result.add(cacheFeedNews.get(eKey));
                }

            } else {

                // 重新排序
                for (String eKey : keys) {
                    result.add(cacheFeedNews.get(eKey));
                }
            }
        }

        // 获取总条数
        totalRecord = findByNewsFromDBCount(feedNews);

        // 初始化评论数
        listCount = initFeedNewsComment(result);
        return new Paging<FeedNewsCount>(totalRecord, feedNews.getPage(), feedNews.getPageSize(), listCount);
    }

    @Override
    public boolean destory(FeedNews feedNews) {
        FeedNews news = feedNewsDAO.getById(feedNews.getId());
        int affect = feedNewsDAO.delete(feedNews);
        if (affect > 0) {
            Page p = feedNewsFactory.generateSub(news);
            affect = feedNewsFactory.generateService(feedNews.getSubType()).remove(p);
            Response response = new Response();
            response.setErrorCode(affect > 0 ? ErrorCode.SUCCESS : ErrorCode.ERROR);
            if (affect > 0 && feedNews.getSubType() == SubType.STATUS) {

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

    /**
     * 封装评论数
     * 
     * @param feedNews
     * @return
     */
    private List<FeedNewsCount> initFeedNewsComment(List<FeedNews> feedNews) {
        List<FeedNewsCount> result = new ArrayList<FeedNewsCount>();
        if (feedNews != null && !feedNews.isEmpty()) {
            List<Long> ids = new ArrayList<Long>();
            List<SubType> types = new ArrayList<SubType>();
            for (FeedNews news : feedNews) {

                // 每一个子类的id
                ids.add(news.getSubNewsId());
                types.add(news.getSubType());
            }
            Map<Long, Integer> commentCounts = commentService.findCommentsCountsByIds(types, ids);
            for (FeedNews f : feedNews) {
                FeedNewsCount feedNewsCount = BeanUtils.feedNews2FeedNewsCount(f);
                if (commentCounts.containsKey(feedNewsCount.getId())) {
                    feedNewsCount.setCommentCount(commentCounts.get(feedNewsCount.getId()));
                } else {
                    feedNewsCount.setCommentCount(0);
                }
                result.add(feedNewsCount);
            }
        }
        return result;
    }

}
