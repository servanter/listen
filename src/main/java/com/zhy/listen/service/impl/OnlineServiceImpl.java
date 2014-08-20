package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.cache.CacheNewFeed;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.JedisClient;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.service.OnlineService;
import com.zhy.listen.solr.SolrService;

@Service
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private SolrService solrService;
    
    @Autowired
    private JedisClient jedisClient;

    @Override
    public int findCurrentTimeInService() {
        return jedisClient.jedis.llen(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX).intValue();
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
        return jedisClient.lrange(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX, 0, -1);
    }

    @Override
    public int pushUsers(List<Long> ids, Timestamp currentTime, FeedNews feedNews) {
        int count = 0;
        CacheNewFeed cacheNewFeed = new CacheNewFeed();
        cacheNewFeed.setNewId(feedNews.getId());
        cacheNewFeed.setCreateTime(currentTime);
        for(int i = 0; i < ids.size(); i++) {
            String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, ids.get(i));
            
            // 推送
            count += jedisClient.lpush(key, cacheNewFeed);
        }
        return count;
    }
    
    @Override
    public int removeUsers(List<Long> ids, Timestamp currentTime, FeedNews feedNews) {
        int count = 0;
        CacheNewFeed cacheNewFeed = new CacheNewFeed();
        cacheNewFeed.setNewId(feedNews.getId());
        cacheNewFeed.setCreateTime(currentTime);
        for(int i = 0; i < ids.size(); i++) {
            String key = KeyGenerator.generateKey(CacheConstants.CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX, ids.get(i));
            count += jedisClient.lrem(key, 0, cacheNewFeed);
        }
        return count;
    }

    @Override
    public boolean login(List<Long> ids) {
        try {
            List<Long> onlineUserIds = jedisClient.lrange(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX, 0, -1, Long.class);
            if (onlineUserIds != null) {
                for (Long id : ids) {
                    if (onlineUserIds.contains(id)) {
                        
                        // 删掉之后重新放入
                        onlineUserIds.remove(id);
                    }
                }
                jedisClient.lpush(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX, ids);
            } else {
                
                // 进来的id应该是数据库DESC过来,所以用rpush
                jedisClient.rpush(CacheConstants.CACHE_ONLINE_USER_IDS_PREFIX, ids);
            }
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // 更新时间
            jedisClient.jedis.set(CacheConstants.CACHE_ONLINE_LAST_TIME, time);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
