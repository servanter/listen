package com.zhy.listen.query;

import java.util.List;

import com.zhy.listen.bean.Paging;

public abstract class AbstractSearch {

    public abstract <T extends Paging> List<T> search(T t);
}
