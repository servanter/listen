package com.zhy.listen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Music;
import com.zhy.listen.bean.MusicUploadEnum;

@Repository
public interface MusicDAO {

    public List<Music> getMusicsByUpload(@Param("uploadEnum") MusicUploadEnum musicUploadEnum);

    public int save(Music music);
}