package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.entities.PointConfig;

@Repository
public interface PointConfigDAO {

    public PointConfig getPointConfigByPoint(Long point);

}
