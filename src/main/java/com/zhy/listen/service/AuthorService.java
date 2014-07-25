package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.entities.Author;

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
    public Paging<Author> findAuthorsByConditions(Author author);

    /**
     * 新增作者
     * 
     * @param author
     * @return
     */
    public boolean add(Author author);

    /**
     * 批量新增作者
     * 
     * @param authors
     */
    public void batchAdd(List<Author> authors);
}
