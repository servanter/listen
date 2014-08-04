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
        FeedNews feedNews = generateFeedNews(page, type);
        int lastId = feedNewsDAO.save(feedNews);

        // 这里应该用消息队列做
        List<Long> ids = friendService.findFriendIds(feedNews.getUserId());
        List<Long> onlineUserIds = onlineService.findAllOnlineUserIds();

        // 我的好友中在线列表
        List<Long> onlineMyUserIds = new ArrayList<Long>();
        for (Long id : ids) {
            if (onlineUserIds.contains(id)) {
                onlineMyUserIds.add(id);
            }
        }
        memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, feedNews), feedNews, CacheConstants.TIME_HOUR * 2);
        
        // push feed news
        onlineService.pushUsers(onlineMyUserIds, new Timestamp(System.currentTimeMillis()), new Long(lastId));
        return lastId > 0;
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

        // 不用list remove 怕数据量太大时 remove慢
        // 在请求时间以后又增加新鲜事 请求时间13:31 流程没处理完 13:32时有增加新鲜事 13:35才处理完成流程
        List<CacheNewFeed> surplusCacheNewFeeds = new ArrayList<CacheNewFeed>();
        List<CacheNewFeed> unreadFeeds = new ArrayList<CacheNewFeed>();
        if (newFeeds != null) {
            for (CacheNewFeed newFeed : newFeeds) {
                if (newFeed.getCreateTime().after(requestTime)) {
                    unreadFeeds.add(newFeed);
                } else {
                    surplusCacheNewFeeds.add(newFeed);
                }
            }
        }

        // 从缓存中取新鲜事
        if (unreadFeeds.size() > 0) {
            String[] keys = new String[unreadFeeds.size()];
            for (int i = 0; i < unreadFeeds.size(); i++) {
                keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_FEED_NEWS, unreadFeeds.get(i));
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
        if (surplusCacheNewFeeds.size() > 0) {
            memcached.set(key, surplusCacheNewFeeds, CacheConstants.TIME_HOUR * 4);
        }
        return result;
    }

    @Override
    public int getUnreadCount(Long userId, Timestamp requestTime) {
        int result = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX,
                userId);
        List<CacheNewFeed> newFeeds = memcached.get(key);
        if (newFeeds != null) {
            for (CacheNewFeed newFeed : newFeeds) {
                if (newFeed.getCreateTime().after(requestTime)) {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public List<FeedNews> findByIds(List<Long> ids) {
        return feedNewsDAO.getByIds(ids);
    }

}
