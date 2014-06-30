package com.zhy.listen.query;

import java.util.List;

import com.zhy.listen.bean.Paging;

public abstract class AbstractSearch<T> {

    public abstract List<T> search(T t);
}
