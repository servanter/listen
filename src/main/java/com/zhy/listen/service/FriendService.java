package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.entities.Friend;

public interface FriendService {

    /**
     * 根据用户ID获取好友列表
     * 
     * @param friend
     * @return
     */
    public Paging<UserStatusPointPath> findFriendsByUserId(Friend friend);

    /**
     * 获取用户好友id列表
     * 
     * @param userId
     * @return
     */
    public List<Long> findFriendIds(Long userId);

    /**
     * 同意加为好友(数据库中两条记录,userid与friendid互换)
     * 
     * @param friend
     * @return
     */
    public boolean makeFriends(Friend friend);

    /**
     * 删除好友关系(逻辑删除数据库中两条记录,userid与friendid互换)
     * 
     * @param friend
     * @return
     */
    public boolean removeFriendRelation(Friend friend);
}
