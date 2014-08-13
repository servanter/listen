package com.zhy.listen.task.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.entities.Music;
import com.zhy.listen.service.MusicService;
import com.zhy.listen.service.UserService;
import com.zhy.listen.solr.SolrService;
import com.zhy.listen.task.TaskScheduling;

/**
 * 索引任务
 * 
 * @author zhanghongyan@outlook.com
 *
 */
@Service("indexTask")
public class IndexTask implements TaskScheduling {

    @Autowired
    private MusicService musicService;
    
    @Autowired
    private SolrService solrService;
    
    @Autowired
    private UserService userService;
    
    public void invoke(){
        List<Music> musics = musicService.findMusicsByIndex(IndexEnum.INDEXED);
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.MUSIC);
        indexer.setNeedIndexList(musics);
        List<UserStatusPointPath> users = userService.findUsersByIndex(IndexEnum.NOT_INDEXED); 
        indexer.setIndexerClass(IndexerClass.USER);
        indexer.setNeedIndexList(users);
        solrService.create(indexer);
    }
}
