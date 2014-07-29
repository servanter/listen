package com.zhy.listen.service;

import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Path;

/**
 * 位置服务
 * 
 * @author zhanghongyan@outlook.com
 *
 */
public interface PathService {

    public boolean sign(Path path);
    
    public QueryResult queryByPath(Path path, Integer mile);
}
