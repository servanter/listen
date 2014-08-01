package com.zhy.listen.dao;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.entities.FeedNews;

@Repository
public interface FeedNewsDAO {

    Paging<FeedNews> getByNews(FeedNews feedNews);

    int save(FeedNews feedNews);

}
