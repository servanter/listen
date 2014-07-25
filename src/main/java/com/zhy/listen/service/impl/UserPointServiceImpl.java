package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.dao.UserPointDAO;
import com.zhy.listen.entities.UserPoint;
import com.zhy.listen.service.UserPointService;

@Service
public class UserPointServiceImpl implements UserPointService {

    @Autowired
    private UserPointDAO userPointDAO;
    
    @Override
    public UserPoint findByUserId(Long userId) {
        return userPointDAO.getByUserId(userId);
    }

    @Override
    public boolean add(UserPoint userPoint) {
        return userPointDAO.save(userPoint) > 0;
    }

}
