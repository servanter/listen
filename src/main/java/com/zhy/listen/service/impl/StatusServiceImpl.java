package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Status;
import com.zhy.listen.dao.StatusDAO;
import com.zhy.listen.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusDAO statusDAO;
    
    @Override
    public Status findUserLastestStatus(Long userId) {
        return statusDAO.getUserLastestStatus(userId);
    }

    @Override
    public boolean post(Status status) {
        return statusDAO.save(status) > 0;
    }

}
