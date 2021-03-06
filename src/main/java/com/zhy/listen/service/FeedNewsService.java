package com.zhy.listen.service;

import java.sql.Timestamp;
import java.util.List;

import com.zhy.listen.bean.FeedNewsCount;
import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.entities.FeedNews;

/**
 * 新鲜事业务
 * 
 * @author zhanghongyan
 * 
 */
public interface FeedNewsService {

    /**
     * 获取某用户新鲜事(点击头像进入个人主页)
     * 
     * @param feedNews
     * @return
     */
    public Paging<FeedNewsCount> findByNews(FeedNews feedNews);

    /**
     * 获取新鲜事详情
     * 
     * @param <T>
     * @param id
     * @param type
     * @return
     */
    public <T extends Object> T findDetailByIdAndType(Long id, SubType type);

    /**
     * 添加新鲜事
     * 
     * @param page
     * @param type
     * @return
     */
    public boolean push(Page page, SubType type);

    /**
     * 获取未读列表
     * 
     * @param userId
     * @param requestTime
     * @return
     */
    public List<FeedNewsCount> findUnreadList(Long userId, Timestamp requestTime);

    /**
     * 获取未读条数
     * 
     * @param userId
     * @param requestTime
     * @return
     */
    public int findUnreadCount(Long userId, Timestamp requestTime);

    /**
     * 删除新鲜事
     * 
     * @param feedNews
     * @return
     */
    public boolean destory(FeedNews feedNews);
    
}
