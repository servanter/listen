package com.zhy.listen.service;

import com.zhy.listen.entities.UserPoint;

/**
 * 用户积分业务
 * 
 * @author zhanghongyan
 *
 */
public interface UserPointService {

    public UserPoint findByUserId(Long userId);
    
    public boolean add(UserPoint userPoint);
}
