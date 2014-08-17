package com.zhy.listen.cache;

/**
 * 缓存常量类
 * 
 * @author zhanghongyan
 *
 */
public class CacheConstants {
    
    /**
     * 1 second
     */
    public static long TIME_SECOND = 1;

    /**
     * 1 minute
     */
    public static long TIME_MINUTE = 60;

    /**
     * 1 hour
     */
    public static long TIME_HOUR = 3600;

    /**
     * 1 day
     */
    public static long TIME_DAY = 86400;
    
    /**
     * 在线列表(id)
     */
    public static final String CACHE_ONLINE_USER_IDS_PREFIX = "OUI";
    
    /**
     * 最后更新在线列表时间
     */
    public static final String CACHE_ONLINE_LAST_TIME = "OLT";
    
    /**
     * 个人缓存好友发送新鲜事池
     */
    public static final String CACHE_ONLINE_USER_OTHERS_PUSH_IMMEDIATELY_NEWS_PREFIX = "OUPIN";
    
    /**
     * 新鲜事实体
     */
    public static final String CACHE_FEED_NEWS = "FN";
    
    /**
     * 个人新鲜事池
     */
    public static final String CACHE_USER_FEED_NEWS = "UFN";
    
    /**
     * 个人新鲜事池数量
     */
    public static final String CACHE_USER_FEED_NEWS_COUNT = "UFNC";
    
    /**
     * 个人好友
     */
    public static final String CACHE_USER_FRIENDS = "UF";
    
    /**
     * 个人好友数量
     */
    public static final String CACHE_USER_FRIENDS_COUNT = "UFC";
    
    /**
     * 个人好友ID列表
     */
    public static final String CACHE_USER_FRIEND_IDS = "UFI";
    
    /**
     * 评论count
     */
    public static final String CACHE_COMMENT_COUNT = "CC";
}
