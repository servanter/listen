package com.zhy.listen.service;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.query.QueryResult;

/**
 * 位置服务
 * 
 * @author zhanghongyan@outlook.com
 *
 */
public interface PathService {

    public boolean sign(UserStatusPointPath path);
    
    public QueryResult queryByPath(UserStatusPointPath path, Integer mile);
}
