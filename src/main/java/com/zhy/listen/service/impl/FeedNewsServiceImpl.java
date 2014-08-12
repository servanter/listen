package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.bean.cache.CacheNewFeed;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.dao.FeedNewsDAO;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;
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
    private Memcached memcached;

    private List<FeedNews> findByNewsFromDB(FeedNews feedNews) {
        return feedNewsDAO.getByNews(feedNews);
    }
    
    private int findByNewsFromDBCount(FeedNews feedNews) {
        return feedNewsDAO.getByNewsCount(feedNews);
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
                String profileNewsKey = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS,
                        feedNews.getUserId());
                Paging<FeedNews> profileNews = memcached.get(profileNewsKey);
                if (profileNews != null && profileNews.getResult() != null && !profileNews.getResult().isEmpty()) {
                    List<FeedNews> news = new ArrayList<FeedNews>();
                    news.add(f);
                    for (FeedNews n : profileNews.getResult()) {
                        news.add(n);
                    }
                    profileNews.setResult(news);
                    profileNews.setTotalRecord(profileNews.getTotalRecord() + 1);
                } else {
                    profileNews = new Paging<FeedNews>();
                    profileNews.setTotalRecord(1L);
                    List<FeedNews> news = new ArrayList<FeedNews>();
                    news.add(f);
                    profileNews.setResult(news);
                }
                memcached.set(profileNewsKey, profileNews, CacheConstants.TIME_HOUR * 2);

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
                        onlineService.pushUsers(onlineMyUserIds, new Timestamp(System.currentTimeMillis()), f.getId());
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
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX,
                userId);
        List<CacheNewFeed> newFeeds = memcached.get(key);

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

                // 设置缓存
                for (FeedNews f : list) {
                    memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);
                }
            }
        }
        memcached.delete(key);
        return result;
    }

    @Override
    public int findUnreadCount(Long userId, Timestamp requestTime) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        List<CacheNewFeed> newFeeds = memcached.get(key);
        return newFeeds != null && newFeeds.size() > 0 ? newFeeds.size() : 0;
    }

    @Override
    public List<FeedNews> findByIds(List<Long> ids) {
        return feedNewsDAO.getByIds(ids);
    }

    /* (non-Javadoc)
     * @see com.zhy.listen.service.FeedNewsService#findBaseFeedsByUserId(java.lang.Long)
     */
    @Override
    public List<FeedNews> findBaseFeedsByUserId(Long userId) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_USER_FEED_NEWS, userId);
        Paging<FeedNews> feedNews = memcached.get(key);
        if (feedNews != null && feedNews.getResult() != null && !feedNews.getResult().isEmpty()
                && feedNews.getResult().size() >= 5) {
            return feedNews.getResult().subList(0, 5);
        } else {
            FeedNews news = new FeedNews();
            news.setUserId(userId);
            news.setPageSize(20);
            List<FeedNews> list = findByNewsFromDB(news);
            if (list != null && !list.isEmpty()) {
                int totalRecord = findByNewsFromDBCount(news);
                memcached.set(key, new Paging<FeedNews>(totalRecord, 1, 20, list), CacheConstants.TIME_HOUR * 2);
                return list.size() >= 5 ? list.subList(0, 5) : list.subList(0, list.size());
            }
        }
        return new ArrayList<FeedNews>();
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
        Paging<FeedNews> list = memcached.get(key);
        if (list != null && list.getResult() != null && !list.getResult().isEmpty()
                && list.getResult().size() >= endIndex) {
            List<FeedNews> s = list.getResult().subList(startIndex, endIndex);
            result.setResult(s);
        } else {

            // 不够条数或者缓存直接为null
            int fetchSize = 20;
            int sinceCount = 0;
            if (list != null && list.getResult() != null) {
                fetchSize = endIndex - list.getResult().size();
                sinceCount = list.getResult().size();
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
                if (list != null && list.getResult() != null) {
                    for(FeedNews f : currentResult) {
                        if(!list.getResult().contains(f)) {
                            list.getResult().add(f);
                        }
                    }
                } else {
                    if(currentResult.size() > startIndex) {
                        List<FeedNews> subList = currentResult.subList(startIndex, currentResult.size());
                        result = new Paging<FeedNews>(totalRecord, feedNews.getPage(), feedNews.getPageSize(), subList);
                    }
                    list = new Paging<FeedNews>(totalRecord, feedNews.getPage(), feedNews.getPageSize(), currentResult);
                    
                }
            } else {
                
                // 没有查询到，那么缓存中有该页的所有数据
                if(list != null && list.getResult() != null && !list.getResult().isEmpty()) {
                    if(list.getResult().size() > startIndex) {
                        List<FeedNews> subList = list.getResult().subList(startIndex, list.getResult().size());
                        result = new Paging<FeedNews>(list.getTotalRecord(), feedNews.getPage(), feedNews.getPageSize(), subList);
                    }
                }
            }
            memcached.set(key, list, CacheConstants.TIME_HOUR * 2);
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
                    onlineService.removeUsers(onlineMyUserIds, new Timestamp(System.currentTimeMillis()), feedNews.getId());
                }
            }
            return true;
        }
        return false;
    }

}
