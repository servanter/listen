package com.zhy.listen.service;

import com.zhy.listen.bean.Paging;

public class AbstractService<T> {

    protected Paging<T> findByPaging() { 
        
        
        
        return new Paging<T>();
        
    }
}
