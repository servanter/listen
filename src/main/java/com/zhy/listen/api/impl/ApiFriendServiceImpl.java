package com.zhy.listen.api.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.api.ApiFriendService;
import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Friend;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.FriendService;

/**
 * 接口友关系业务实现类
 * 
 * @author zhanghongyan
 * 
 */
@Service
public class ApiFriendServiceImpl implements ApiFriendService {

    @Autowired
    private FriendService friendService;

    /*
     * (non-Javadoc)
     * 
     * @see com.zhy.listen.api.ApiFriendService#findFriendsByUserId(com.zhy.listen.entities.Friend)
     */
    @Override
    public QueryResult findFriendsByUserId(Friend friend) {
        Paging<UserStatusPointPath> list = friendService.findFriendsByUserId(friend);
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(list.getResult());
        queryResult.setHitCount(list.getResult().size());
        queryResult.setPage(list.getPage());
        queryResult.setPageSize(list.getPageSize());
        queryResult.setSinceCount(list.getSinceCount());
        queryResult.setTotalPage(list.getTotalPage());
        queryResult.setTotalRecord(list.getTotalRecord());
        queryResult.setErrorCode(ErrorCode.SUCCESS);
        queryResult.setEndTime(new Timestamp(System.currentTimeMillis()));
        return queryResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zhy.listen.api.ApiFriendService#makeFriends(java.lang.Long, java.lang.Long)
     */
    @Override
    public Response makeFriends(Long userId, Long friendId) {
        boolean isSuccess = friendService.makeFriends(new Friend(userId, friendId));
        Response response = new Response(ErrorCode.SUCCESS, isSuccess);
        return response;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zhy.listen.api.ApiFriendService#destory(java.lang.Long, java.lang.Long)
     */
    @Override
    public Response destory(Long userId, Long friendId) {
        boolean isSuccess = friendService.removeFriendRelation(new Friend(userId, friendId));
        Response response = new Response(ErrorCode.SUCCESS, isSuccess);
        return response;
    }

}
