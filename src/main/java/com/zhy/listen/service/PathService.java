package com.zhy.listen.service;

import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.query.QueryResult;

/**
 * 位置服务
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public interface PathService {

    /**
     * 标记当前位置
     * 
     * @param path
     * @return
     */
    public boolean sign(UserStatusPointPath path);

    /**
     * 查询附近的人
     * 
     * @param path
     * @param mile
     * @return
     */
    public QueryResult queryByPath(UserStatusPointPath path, Integer mile);

    /**
     * 清空地理信息
     * 
     * @param path
     * @return
     */
    public Response pathSetting(UserStatusPointPath path);
}
