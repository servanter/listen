package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.entities.UserPoint;

@Repository
public interface UserPointDAO {

    UserPoint getByUserId(Long userId);

    int save(UserPoint userPoint);

}
