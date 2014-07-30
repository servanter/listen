package com.zhy.listen.solr;

import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.query.QueryResult;

/**
 * solr业务层
 * @author zhanghongyan@outlook.com
 *
 */
public interface SolrService {

    public boolean create(Indexer indexer);
    
    public QueryResult query(QueryResult queryResult);
    
    public QueryResult queryPath(QueryResult queryResult);
}
