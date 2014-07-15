package com.zhy.listen.series.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Author;
import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.query.SearchService;
import com.zhy.listen.series.SeriesService;
import com.zhy.listen.service.AuthorService;
import com.zhy.listen.service.FetchMusicService;
import com.zhy.listen.service.MusicService;
import com.zhy.listen.solr.SolrService;
import com.zhy.listen.util.StringUtil;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private SolrService solrService;
    
    @Autowired
    private FetchMusicService fetchMusicService;
    
    @Autowired
    private MusicService musicService;
    
    @Override
    public QueryResult findMusicByText(QueryResult queryResult) {
        String text = queryResult.getKeywords();
        String str = text;
        Music music = new Music();
        Paging<Author> authors = authorService.findAuthorsByConditions(new Author());
        for(Author a : authors.getResult()) {
            if(text.contains(a.getName())) {
                music.setAuthor(a.getName());
                str = str.replace(a.getName(), "");
                music.setTitle(str);
                break;
            }
        }
        List<QueryField> fields = new ArrayList<QueryField>();
        if(!StringUtil.isNullOrEmpty(music.getAuthor())) {
            QueryField queryField = new QueryField("author", music.getAuthor());
            fields.add(queryField);
        }
        if(!StringUtil.isNullOrEmpty(music.getTitle())) {
            QueryField queryField = new QueryField("title", music.getTitle());
            fields.add(queryField);
        }
        queryResult.setQueryFields(fields);
        queryResult = solrService.query(queryResult);
        if(queryResult.getHitCount() <= 0) {
            try {
                Music m = fetchMusicService.findMusicByBaidu(music.getAuthor(), music.getTitle());
                boolean isSuccess = musicService.add(m);
                if(isSuccess) {
                    queryResult.setHitCount(1);
                    List<Music> list = new ArrayList<Music>();
                    list.add(m);
                    queryResult.setResult(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queryResult;
    }

}
