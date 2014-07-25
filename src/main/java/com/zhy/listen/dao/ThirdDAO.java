package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.entities.Third;

@Repository
public interface ThirdDAO {

    /**
     * 存储第三方授权信息
     * 
     * @param third
     * @return
     */
    public int save(Third third);

    /**
     * 根据用户ID获取第三方信息
     * 
     * @param userId
     * @return
     */
    public List<Third> getThirdsById(Long userId);

    /**
     * 根据type获取同一授权开放平台的信息
     * 
     * @param third
     * @return
     */
    public List<Long> getSameThirdsByType(Third third);

}
