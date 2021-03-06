package com.zhy.listen.api;

import java.sql.Timestamp;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.FeedNews;

public interface ApiFeedNewsService {

    /**
     * 获取未读条数
     * 
     * @param userId
     * @param requestTime
     * @return
     */
    public Response findUnreadCount(Long userId, Timestamp requestTime);

    /**
     * 删除新鲜事
     * 
     * @param feedNews
     * @return
     */
    public Response destory(FeedNews feedNews);
    
    /**
     * 获取未读列表
     * 
     * @param userId
     * @param requestTime
     * @return
     */
    public QueryResult findUnreadList(Long userId, Timestamp requestTime);
    
    /**
     * 获取详情
     * 
     * @param id
     * @param subType
     * @return
     */
    public Response findDetail(Long id, SubType subType);
}
