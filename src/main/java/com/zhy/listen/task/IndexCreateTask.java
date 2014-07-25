package com.zhy.listen.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.create.CreateService;
import com.zhy.listen.entities.Music;
import com.zhy.listen.service.MusicService;

/**
 * 创建索引
 * @author zhanghongyan@outlook.com
 *
 */
@Component
public class IndexCreateTask {

    @Autowired
    private MusicService musicService;
    
    @Autowired
    private CreateService createService;
    
    public void create(){
        List<Music> musics = musicService.findMusicsByIndex(IndexEnum.INDEXED);
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.MUSIC);
        indexer.setNeedIndexList(musics);
        boolean isSuccess = createService.found(indexer);
        System.out.println(isSuccess);
        
    }
}
