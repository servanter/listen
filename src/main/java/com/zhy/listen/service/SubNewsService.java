package com.zhy.listen.service;


/**
 * 子类新鲜事
 * 
 * @author zhanghongyan
 *
 */
public interface SubNewsService<T> {

    public T findById(Long id);
    
    public int remove(T t);
}
