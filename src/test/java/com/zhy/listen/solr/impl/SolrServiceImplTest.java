package com.zhy.listen.solr.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
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
        List<UserStatusPointPath> users = userService.findUsersByIndex(IndexEnum.NOT_INDEXED);
//        Path path = new Path();
//        path.setId(5L);
//        path.setUserId(1L);
//        path.setProvince("");
//        path.setCity("北京");
//        path.setLoc("39.914889,116.403874");
//        path.setIsClean(false);
//        path.setDiscoveryTime(new Timestamp(System.currentTimeMillis()));
//        List<Path> paths = new ArrayList<Path>();
//        paths.add(path);
        indexer.setNeedIndexList(users);
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
