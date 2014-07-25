package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.dao.PointConfigDAO;
import com.zhy.listen.entities.PointConfig;
import com.zhy.listen.service.PointConfigService;

@Service
public class PointConfigServiceImpl implements PointConfigService {

    @Autowired
    private PointConfigDAO pointConfigDAO;
    
    @Override
    public PointConfig findPointConfigByPoint(Long point) {
        return pointConfigDAO.getPointConfigByPoint(point);
    }

}
