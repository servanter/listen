package com.zhy.listen.api;

import com.zhy.listen.bean.Response;
import com.zhy.listen.entities.User;

/**
 * 用户接口
 * 
 * @author zhanghongyan
 *
 */
public interface ApiUserService {

    /**
     * 修改用户信息
     * 
     * @param user
     * @return
     */
    public Response modifyUserInfo(User user);
}
