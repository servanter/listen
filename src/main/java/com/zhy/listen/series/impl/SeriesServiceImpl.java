package com.zhy.listen.series.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Author;
import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.query.SearchService;
import com.zhy.listen.series.SeriesService;
import com.zhy.listen.service.AuthorService;
import com.zhy.listen.service.FetchMusicService;
import com.zhy.listen.service.MusicService;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private FetchMusicService fetchMusicService;
    
    @Autowired
    private MusicService musicService;
    
    @Override
    public QueryResult findMusicByText(String text) {
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
        music.setAuthor("汪");
        music.setTitle(null);
        QueryResult queryResult = searchService.search(music);
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
