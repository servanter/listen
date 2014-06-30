package com.zhy.listen.create.impl;

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
import com.zhy.listen.create.CreateService;

public class CreateServiceImplTest extends SuperTest{

    @Autowired
    private CreateService createService;
    
    @Test
    public void testFound() {
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.MUSIC);
        List<Music> ms = new ArrayList<Music>();
        Music music = new Music();
        music.setId(111L);
        music.setTitle("回到过去");
        music.setAuthor("周杰伦");
        music.setUrl("www.baidu.com");
        music.setIsUpload(false);
        music.setIsIndex(false);
        music.setIsValid(true);
        music.setLrc("asdasdasdadasdasd");
        music.setCreateTime(new Timestamp(System.currentTimeMillis()));
        ms.add(music);
        indexer.setNeedIndexList(ms);
        createService.found(indexer);
    }

}
