package com.zhy.listen.index;

import java.util.List;

import org.apache.lucene.document.Document;

public interface GenerateIndexService<T> {

    public abstract List<Document> create(List<T> list);
}
