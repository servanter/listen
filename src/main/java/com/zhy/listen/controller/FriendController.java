package com.zhy.listen.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhy.listen.api.ApiFriendService;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Friend;

/**
 * 好友关系web层
 * 
 * @author zhanghongyan
 * 
 */
@Controller
@RequestMapping("/friend")
public class FriendController extends CommonController {

    @Autowired
    private ApiFriendService apiFriendService;

    @RequestMapping("makeFriend")
    public String makeFriend(@RequestParam("user_id") Long userId, @RequestParam("friend_id") Long friendId, HttpServletResponse response) throws Exception {
        Response resp = apiFriendService.makeFriends(userId, friendId);
        print(resp, response);
        return null;
    }

    @RequestMapping("destory")
    public String destory(@RequestParam("user_id") Long userId, @RequestParam("friend_id") Long friendId, HttpServletResponse response) throws Exception {
        Response resp = apiFriendService.destory(userId, friendId);
        print(resp, response);
        return null;
    }

    @RequestMapping("myFriends")
    public String myFriends(@RequestParam("user_id") Long userId, @RequestParam("page") Integer page, @RequestParam("page_size") Integer pageSize, HttpServletResponse response) throws Exception {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setPage(page);
        friend.setPageSize(pageSize);
        QueryResult queryResult = apiFriendService.findFriendsByUserId(friend);
        print(queryResult, response);
        return null;
    }

}
