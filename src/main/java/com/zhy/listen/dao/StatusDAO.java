package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Status;

@Repository
public interface StatusDAO {

    Status getUserLastestStatus(Long userId);

    int save(Status status);

}
