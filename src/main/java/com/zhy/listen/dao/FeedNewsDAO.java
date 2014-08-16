package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.FeedNewsCount;
import com.zhy.listen.entities.FeedNews;

@Repository
public interface FeedNewsDAO {

    List<FeedNewsCount> getByNews(FeedNews feedNews);

    int save(FeedNews feedNews);

    List<FeedNewsCount> getByIds(List<Long> ids);

    FeedNewsCount getById(Long id);

    int getByNewsCount(FeedNews feedNews);
    
    int delete(FeedNews feedNews);
}
