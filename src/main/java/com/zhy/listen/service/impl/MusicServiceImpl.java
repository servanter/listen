package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.MusicUploadEnum;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.dao.MusicDAO;
import com.zhy.listen.query.AbstractLuceneSearch;
import com.zhy.listen.service.MusicService;

@Service("musicService")
public class MusicServiceImpl extends AbstractLuceneSearch<Music> implements MusicService {

    @Autowired
    private MusicDAO musicDAO;
    
    @Override
    public List<Music> findNotUploadMusics() {
        return musicDAO.getMusicsByUpload(MusicUploadEnum.NOT_UPLOADED);
    }

    @Override
    public boolean add(Music music) {
        return musicDAO.save(music) > 0;
    }

    @Override
    public List<Music> findByAuthorAndTitle(String author, String title) {
        return musicDAO.getMusic(author, title);
    }

    @Override
    public List<Music> search(Music t) {
        
        // 查询索引
        QueryResult queryResult = super.generateQueryResult(t, IndexerClass.MUSIC);
        queryResult = super.query(queryResult);
        if(queryResult.getHitCount() > 0) {
            return (List<Music>) queryResult.getResult();
        }
        return (List<Music>)musicDAO.getMusic(t.getAuthor(), t.getTitle());
    }



}
