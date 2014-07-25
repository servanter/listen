package com.zhy.listen.service;

import com.zhy.listen.bean.PointDetail;

/**
 * 积分业务
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public interface PointDetailService {

    /**
     * 存储积分详情
     * 
     * @param point
     * @return
     */
    public boolean add(PointDetail point);
}
