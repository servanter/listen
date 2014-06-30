package com.zhy.listen.create;

import com.zhy.listen.bean.indexer.Indexer;

public interface CreateService {

    /**
     * 创建索引
     * 
     * @param indexer
     * @return
     */
    public boolean found(Indexer indexer);
}
