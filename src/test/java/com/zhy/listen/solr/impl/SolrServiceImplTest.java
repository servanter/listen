package com.zhy.listen.solr.impl;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.solr.SolrService;

public class SolrServiceImplTest extends SuperTest {

    @Autowired
    private SolrService solrService;
    
    @Test
    public void testCreate() {
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.MUSIC);
        List<Music> ms = new ArrayList<Music>();
        Music music = new Music();
        music.setId(333L);
        music.setTitle("布拉格广场");
        music.setAuthor("蔡依林");
        music.setUrl("www.baidu.com");
        music.setIsUpload(false);
        music.setIsIndex(false);
        music.setIsValid(true);
        music.setLrc("asdasdasdadasdasd");
        music.setCreateTime(new Timestamp(System.currentTimeMillis()));
        ms.add(music);
        indexer.setNeedIndexList(ms);
        solrService.create(indexer);
    }

    @Test
    public void testQuery() {
    }

}
