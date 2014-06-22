package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.Music;

public interface BaseMusicService {

    /**
     * 查询没有被上传的数据
     * @return
     */
    public List<Music> findNotUploadMusics();
    
    /**
     * 添加
     * @param music
     * @return
     */
    public boolean add(Music music);
}
