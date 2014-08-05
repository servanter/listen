package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.entities.Third;
import com.zhy.listen.entities.User;


/**
 * 第三方业务层
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public interface ThirdService {

    /**
     * 存储第三方授权信息
     * 
     * @param attribute
     * @return
     */
    public Third add(Third attribute);

    /**
     * 根据用户id获取第三方信息
     * 
     * @param userId
     * @return
     */
    public List<Third> findThirdsById(Long userId);

    /**
     * 随机获取授权统一开放平台的用户
     * 
     * @param user
     * @return
     */
    public List<Long> findSameThird(User user);
}
