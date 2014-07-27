package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.entities.Path;
import com.zhy.listen.entities.User;

/**
 * 位置服务
 * 
 * @author zhanghongyan@outlook.com
 *
 */
public interface PathService {

    public boolean sign(Path path);
    
    public List<User> queryByPath(Path path, Integer mile);
}
