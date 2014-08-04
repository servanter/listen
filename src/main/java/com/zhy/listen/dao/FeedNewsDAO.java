package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.entities.FeedNews;

@Repository
public interface FeedNewsDAO {

    Paging<FeedNews> getByNews(FeedNews feedNews);

    int save(FeedNews feedNews);

    List<FeedNews> getByIds(List<Long> ids);

    FeedNews getById(Long id);
}
