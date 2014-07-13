package com.zhy.listen.create;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

public interface GenerateIndexService<T> {

    public abstract List<SolrInputDocument> create(List<T> list);
}
