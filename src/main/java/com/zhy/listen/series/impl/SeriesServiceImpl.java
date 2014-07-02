package com.zhy.listen.series.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.bean.Author;
import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.query.SearchService;
import com.zhy.listen.series.SeriesService;
import com.zhy.listen.service.AuthorService;

public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private AuthorService authorService;
    
    @Override
    public QueryResult findMusicByText(String text) {
        authorService.findAuthorsByConditions(new Author());
        return null;
    }

}
