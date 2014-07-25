package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.PointDetail;
import com.zhy.listen.dao.PointDetailDAO;
import com.zhy.listen.service.PointDetailService;

@Service
public class PointDetailServiceImpl implements PointDetailService {

    @Autowired
    private PointDetailDAO pointDetailDAO;
    
    @Override
    public boolean add(PointDetail point) {
        return pointDetailDAO.save(point) > 0;
    }

}
