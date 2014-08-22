package com.zhy.listen.api;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Friend;

/**
 * 好友关系对外接口
 * 
 * @author zhanghongyan
 *
 */
public interface ApiFriendService {

    /**
     * 根据用户ID获取好友列表
     * 
     * @param friend
     * @return
     */
    QueryResult findFriendsByUserId(Friend friend);

    /**
     * 同意加为好友(数据库中两条记录,userid与friendid互换)
     * 
     * @param userId
     * @param friendId
     * @return
     */
    Response makeFriends(Long userId, Long friendId);

    /**
     * 删除好友关系(逻辑删除数据库中两条记录,userid与friendid互换)
     * 
     * @param userId
     * @param friendId
     * @return
     */
    Response destory(Long userId, Long friendId);
}
