package com.zhy.listen.service;

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
     * 获取新鲜事
     * 
     * @param feedNews
     * @return
     */
    public Paging<FeedNews> findByNews(FeedNews feedNews);

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
    public boolean add(Page page, SubType type);
}
