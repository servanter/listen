package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Friend;

/**
 * 好友DAO层
 * 
 * @author zhy19890221@gmail.com
 * 
 */
@Repository
public interface FriendDAO {

    /**
     * 添加好友
     * 
     * @param friend
     * @return
     */
    public Friend save(Friend friend);

    /**
     * 根据用户ID取好友信息
     * 
     * @param userId
     * @return
     */
    public List<Friend> getFriendsByUserId(Friend friend);

    /**
     * 根据用户ID,好友ID,逻辑更新好友关系,并修改时间
     * 
     * @param friend
     * @return
     */
    public int updateRelation(Friend friend);

    /**
     * 获取详细信息
     * 
     * @param id
     * @return
     */
    public Friend getFriendById(Long id);

    /**
     * 获取用户好友数量
     * 
     * @param userId
     * @return
     */
    public int getUserFriendSize(Long userId);

}
