package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Author;

/**
 * 作者字典业务
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public interface AuthorService {

    /**
     * 根绝条件获取作者
     * 
     * @param author
     * @return
     */
    public List<Author> getAuthorsByConditions(Author author);
}
