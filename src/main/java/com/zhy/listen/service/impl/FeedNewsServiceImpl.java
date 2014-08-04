package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
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
    private Memcached memcached;

    @Override
    public Paging<FeedNews> findByNews(FeedNews feedNews) {
        return feedNewsDAO.getByNews(feedNews);
    }

    @Override
    public <T extends Object> T findDetailByIdAndType(Long id, SubType type) {
        return (T) feedNewsFactory.generateService(type).findById(id);
    }

    @Override
    public boolean push(Page page, SubType type) {
        try {
            
            FeedNews feedNews = generateFeedNews(page, type);
            feedNewsDAO.save(feedNews);
            
            // 重新查询
            FeedNews f = feedNewsDAO.getById(feedNews.getId());
            memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);
            
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private FeedNews generateFeedNews(Page page, SubType type) {
        if (type == SubType.STATUS) {
            Status status = (Status) page;
            FeedNews feedNews = new FeedNews();
            feedNews.setSubNewsId(status.getId());
            feedNews.setUserId(status.getUserId());
            feedNews.setContent(status.getContent());
            feedNews.setSubType(SubType.STATUS);
            feedNews.setCreateTime(status.getCreateTime());
            return feedNews;
        }
        return null;
    }

    @Override
    public List<FeedNews> getUnreadList(Long userId, Timestamp requestTime) {
        List<FeedNews> result = new ArrayList<FeedNews>();
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        List<CacheNewFeed> newFeeds = memcached.get(key);

        // 从缓存中取新鲜事
        if (newFeeds.size() > 0) {
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
                for(FeedNews f : list) {
                    memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, f.getId()), f, CacheConstants.TIME_HOUR * 2);
                }
            }
        }
        memcached.delete(key);
        return result;
    }

    @Override
    public int getUnreadCount(Long userId, Timestamp requestTime) {
        int result = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, userId);
        List<CacheNewFeed> newFeeds = memcached.get(key);
//        if (newFeeds != null) {
//            for (CacheNewFeed newFeed : newFeeds) {
//                if (newFeed.getCreateTime().after(requestTime)) {
//                    result++;
//                }
//            }
//        }
        return newFeeds != null && newFeeds.size() > 0 ? newFeeds.size() : 0;
    }

    @Override
    public List<FeedNews> findByIds(List<Long> ids) {
        return feedNewsDAO.getByIds(ids);
    }

}
