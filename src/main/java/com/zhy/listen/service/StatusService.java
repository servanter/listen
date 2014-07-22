package com.zhy.listen.service;

import com.zhy.listen.bean.Status;

/**
 * 状态业务层
 *
 * @author zhanghongyan@outlook.com
 *
 */
public interface StatusService {

    /**
     * 根据用户ID获取最近一条状态
     * @param userId
     * @return
     */
    public Status findUserLastestStatus(Long userId);
    
    /**
     * 发布一条状态
     * @param status
     * @return
     */
    public boolean post(Status status);
}
