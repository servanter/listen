package com.zhy.listen.service;

import com.zhy.listen.entities.PointConfig;

/**
 * 积分配置
 * 
 * @author zhanghongyan@outlook.com
 *
 */
public interface PointConfigService {

    /**
     * 根据积分获取配置
     * 
     * @param point
     * @return
     */
    public PointConfig findPointConfigByPoint(Long point);
}
