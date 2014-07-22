package com.zhy.listen.solr.impl;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.User;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.bean.view.UserStatus;
import com.zhy.listen.service.MusicService;
import com.zhy.listen.service.UserService;
import com.zhy.listen.solr.SolrService;

public class SolrServiceImplTest extends SuperTest {

    @Autowired
    private SolrService solrService;
    
    @Autowired
    private MusicService musicService;
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testCreate() {
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.USER);
//        List<Music> musics = musicService.findMusicsByIndex(IndexEnum.NOT_INDEXED);
        List<UserStatus> users = userService.findUsersByIndex(IndexEnum.NOT_INDEXED);
        indexer.setNeedIndexList(users);
        solrService.create(indexer);
    }

    @Test
    public void testQuery() {
        QueryResult queryResult = new QueryResult();
        List<QueryField> fields = new ArrayList<QueryField>();
//        QueryField field = new QueryField("author", "蔡依林");
//        fields.add(field);
//        queryResult.setQueryFields(fields);
        queryResult.setIndexerClass(IndexerClass.USER);
        queryResult.setKeywords("我试个英雄哈哈");
        System.out.println(solrService.query(queryResult));
    }

}
