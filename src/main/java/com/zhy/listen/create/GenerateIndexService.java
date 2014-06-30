package com.zhy.listen.create;

import java.util.List;

import org.apache.lucene.document.Document;

public interface GenerateIndexService<T> {

    public abstract List<Document> create(List<T> list);
}
