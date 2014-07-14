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
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
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
        music.setId(22L);
        music.setTitle("怒放的生命");
        music.setAuthor("汪峰");
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
        QueryResult queryResult = new QueryResult();
        List<QueryField> fields = new ArrayList<QueryField>();
//        QueryField field = new QueryField("author", "蔡依林");
//        fields.add(field);
//        queryResult.setQueryFields(fields);
        queryResult.setIndexerClass(IndexerClass.MUSIC);
        queryResult.setKeywords("我试个啊速第七章第七章度进空间按法律框架萨芬广场阿斯达");
        System.out.println(solrService.query(queryResult));
    }

}
