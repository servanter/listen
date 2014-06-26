package com.zhy.listen.index;

import com.zhy.listen.bean.query.QueryResult;

public interface QueryService {

    /**
     * lucene查询 (目前查询则为时间有新到旧查询)
     * 
     * @param queryResult
     * @return
     */
    public QueryResult query(QueryResult queryResult);

}
