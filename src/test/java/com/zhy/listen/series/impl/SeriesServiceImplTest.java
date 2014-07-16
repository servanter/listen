package com.zhy.listen.series.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.series.SeriesService;

public class SeriesServiceImplTest extends SuperTest {

    @Autowired
    private SeriesService seriesService;
    
    @Test
    public void testFindMusicByText() {
        QueryResult queryResult = new QueryResult();
        queryResult.setKeywords("汪峰怒放的生命");
        queryResult.setIndexerClass(IndexerClass.MUSIC);
        System.out.println(seriesService.findMusicByText(queryResult));;
    }

}
