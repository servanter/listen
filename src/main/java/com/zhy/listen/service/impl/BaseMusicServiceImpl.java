package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.MusicUploadEnum;
import com.zhy.listen.dao.MusicDAO;
import com.zhy.listen.service.BaseMusicService;

@Service
public class BaseMusicServiceImpl implements BaseMusicService {

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

}
