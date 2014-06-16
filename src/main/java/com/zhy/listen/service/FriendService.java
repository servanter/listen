package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Friend;


public interface FriendService {

    /**
     * 根据用户ID获取好友列表
     * 
     * @param friend
     * @return
     */
    public List<Friend> getFriendsByUserId(Friend friend);
    
    /**
     * 获取用户拥有的好友数
     * 
     * @param userId
     * @return
     */
    public int getUserHaveFriendCount(Long userId); 
    
    /**
     * 同意加为好友(数据库中两条记录,userid与friendid互换)
     * 
     * @param friend
     * @return
     */
    public Friend makeFriends(Friend friend);

    /**
     * 删除好友关系(逻辑删除数据库中两条记录,userid与friendid互换)
     * 
     * @param friend
     * @return
     */
    public boolean modifyFriendRelation(Friend friend);
}
