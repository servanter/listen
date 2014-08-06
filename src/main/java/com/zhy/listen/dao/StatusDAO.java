package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.entities.Status;

@Repository
public interface StatusDAO {

    Status getUserLastestStatus(Long userId);

    int save(Status status);
    
    Status getById(Long id);

    int updateIsValid(Status status);

}
