package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.cache.CacheNewFeed;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.service.OnlineService;
import com.zhy.listen.solr.SolrService;

@Service
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
        List<Long> hasUserCacheIds = new ArrayList<Long>();
        Map<String, List<CacheNewFeed>> currentUserCacheNews = memcached.getMulti(keys);
        if(currentUserCacheNews != null && currentUserCacheNews.size() > 0) {
            Iterator<String> it = currentUserCacheNews.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                List<CacheNewFeed> userCacheNewFeeds = currentUserCacheNews.get(key);
                userCacheNewFeeds.add(cacheNewFeed);
                memcached.set(key, userCacheNewFeeds, CacheConstants.TIME_HOUR * 4);                
                hasUserCacheIds.add(Long.parseLong(KeyGenerator.getRowObject(key, CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX)));
                count++;
            }
            
            // 验证是否所有id都对应有缓存
            if(ids.size() != currentUserCacheNews.size()) {
                for(Long id : ids) {
                    if(!hasUserCacheIds.contains(id)) {
                        List<CacheNewFeed> list = new ArrayList<CacheNewFeed>();
                        list.add(cacheNewFeed);
                        memcached.set(KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, id), list, CacheConstants.TIME_HOUR * 4);
                        count++;
                    }
                }
            }
        } else {
            for(Long id : ids) {
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
                        
                        // 删掉之后重新放入
                        onlineUserIds.remove(id);
                        onlineUserIds.add(id);
                    } else {
                        onlineUserIds.add(id);
                    }
                }
            } else {
                onlineUserIds = new ArrayList<Long>();
                onlineUserIds.addAll(ids);
            }
            memcached.set(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX, onlineUserIds, CacheConstants.TIME_MINUTE * 10);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // 更新时间
            memcached.set(CacheConstants.CACHE_ONLINE_LAST_TIME, time, CacheConstants.TIME_MINUTE * 10);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
