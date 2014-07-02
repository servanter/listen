package com.zhy.listen.query;

import com.zhy.listen.bean.query.QueryResult;

public abstract class AbstractSearch<T> {

    public abstract QueryResult search(T t);
}
