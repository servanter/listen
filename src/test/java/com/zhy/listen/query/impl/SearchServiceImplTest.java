package com.zhy.listen.query.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.query.SearchService;

public class SearchServiceImplTest extends SuperTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() {
        Music music = new Music();
        music.setAuthor("周杰伦");
        music.setTitle("回到过去");
        List<Music> ms = searchService.search(music);

        System.out.println(ms);
    }

}
