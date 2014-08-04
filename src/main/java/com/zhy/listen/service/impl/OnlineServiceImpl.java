package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.cache.CacheNewFeed;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.service.OnlineService;
import com.zhy.listen.solr.SolrService;

public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private Memcached memcached;

    @Autowired
    private SolrService solrService;

    @Override
    public int findCurrentTimeInService() {
        return memcached.get(CacheConstants.CACHE_ONLINE_COUNT_PREFIX);
    }

    @Override
    public List<UserStatusPointPath> findLastNews(int pageSize) {
        QueryResult queryResult = new QueryResult();
        queryResult.setIndexerClass(IndexerClass.USER);
        queryResult.setPage(pageSize);
        solrService.query(queryResult);
        if (queryResult.getHitCount() > 0) {
            return (List<UserStatusPointPath>) queryResult.getResult();
        }
        return new ArrayList<UserStatusPointPath>();
    }

    @Override
    public List<Long> findAllOnlineUserIds() {
        return memcached.get(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX);
    }

    @Override
    public int pushUsers(List<Long> ids, Timestamp currentTime, Long newId) {
        String[] keys = new String[ids.size()];
        for(int i = 0; i < ids.size(); i++) {
            keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, ids.get(i));
        }
        
        // 推送
        CacheNewFeed cacheNewFeed = new CacheNewFeed();
        cacheNewFeed.setCreateTime(currentTime);
        cacheNewFeed.setNewId(newId);
        
        int count = 0;
        // 之前没有缓存
        List<Long> needPutUserCacheNewIds = new ArrayList<Long>();
        List<Long> hasUserCacheIds = new ArrayList<Long>();
        Map<String, List<CacheNewFeed>> currentUserCacheNews = memcached.getMulti(keys);
        if(currentUserCacheNews != null && currentUserCacheNews.size() > 0) {
            Iterator<String> it = currentUserCacheNews.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                List<CacheNewFeed> userCacheNewFeeds = currentUserCacheNews.get(key);
                userCacheNewFeeds.add(cacheNewFeed);
                hasUserCacheIds.add(Long.parseLong(KeyGenerator.getRowObject(key, CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX)));
                count++;
            }
        }

        // put之前没有缓存的用户
        for (Long id : hasUserCacheIds) {
            if (!ids.contains(id)) {
                needPutUserCacheNewIds.add(id);
            }
        }
        if (needPutUserCacheNewIds.size() > 0) {
            for(Long id : needPutUserCacheNewIds) {
                List<CacheNewFeed> list = new ArrayList<CacheNewFeed>();
                list.add(cacheNewFeed);
                memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, id), list, CacheConstants.TIME_HOUR * 4);
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean login(List<Long> ids) {
        try {
            List<Long> onlineUserIds = memcached.get(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX);
            if (onlineUserIds != null) {
                for (Long id : ids) {
                    if (onlineUserIds.contains(id)) {
                        onlineUserIds.remove(id);
                    } else {
                        onlineUserIds.add(id);
                    }
                }
            }
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // 更新时间
            memcached.set(CacheConstants.CACHE_ONLINE_LAST_TIME, time, CacheConstants.TIME_MINUTE * 5);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
