package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.PointDetail;

@Repository
public interface PointDetailDAO {

    int save(PointDetail point);

}
