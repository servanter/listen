package com.zhy.listen.service;

import java.sql.Timestamp;
import java.util.List;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.entities.FeedNews;

/**
 * 在线业务
 * 
 * @author zhanghongyan
 * 
 */
public interface OnlineService {

    /**
     * 获取当前在线人数
     * 
     * @return
     */
    public int findCurrentTimeInService();

    /**
     * 获取最后发布状态的人
     * 
     * @return
     */
    public List<UserStatusPointPath> findLastNews(int pageSize);

    /**
     * 获取当前在线id列表
     * 
     * @return
     */
    public List<Long> findAllOnlineUserIds();

    /**
     * 推送给在线好友
     * 
     * @param ids
     * @param currentTime
     * @param newId
     * @return
     */
    public int pushUsers(List<Long> ids, Timestamp currentTime, FeedNews feedNews);

    /**
     * 删除在线好友缓存中新鲜事ID
     * 
     * @param ids
     * @param currentTime
     * @param newId
     * @return
     */
    public int removeUsers(List<Long> ids, Timestamp currentTime, FeedNews feedNews);
    
    /**
     * 更新在线列表
     * 
     * @param id
     * @return
     */
    public boolean login(List<Long> id);

}
