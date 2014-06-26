package com.zhy.listen.query;

import java.util.List;

import com.zhy.listen.bean.Paging;

public interface SearchService {

    public <T extends Paging> List<T> search(T t);
}
