package com.zhy.listen.solr.impl;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.UserStatus;
import com.zhy.listen.bean.UserStatusPoint;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Music;
import com.zhy.listen.entities.Path;
import com.zhy.listen.entities.User;
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
        indexer.setIndexerClass(IndexerClass.PATH);
//        List<Music> musics = musicService.findMusicsByIndex(IndexEnum.NOT_INDEXED);
//        List<UserStatusPoint> users = userService.findUsersByIndex(IndexEnum.NOT_INDEXED);
        Path path = new Path();
        path.setId(5L);
        path.setUserId(1L);
        path.setProvince("");
        path.setCity("北京");
        path.setLoc("129.21,130.04");
        path.setIsClean(false);
        path.setDiscoveryTime(new Timestamp(System.currentTimeMillis()));
        List<Path> paths = new ArrayList<Path>();
        paths.add(path);
        indexer.setNeedIndexList(paths);
        solrService.create(indexer);
    }

    @Test
    public void testQuery() {
        QueryResult queryResult = new QueryResult();
        List<QueryField> fields = new ArrayList<QueryField>();
        QueryField field = new QueryField("province", "北京");
//        QueryField field2 = new QueryField("mileage", "50");
//        fields.add(field);
//        fields.add(field2);
//        queryResult.setQueryFields(fields);
        queryResult.setIndexerClass(IndexerClass.USER);
//        queryResult.setKeywords("等级");
        fields.add(field);
        queryResult.setQueryFields(fields);
        System.out.println(solrService.query(queryResult));
    }

}
