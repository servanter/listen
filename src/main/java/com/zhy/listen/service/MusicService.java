package com.zhy.listen.service;

import java.util.List;

import com.zhy.listen.bean.IndexEnum;
import com.zhy.listen.bean.Music;

/**
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public interface MusicService {

    /**
     * 查询没有被上传的数据
     * 
     * @return
     */
    public List<Music> findNotUploadMusics();

    /**
     * 添加
     * 
     * @param music
     * @return
     */
    public boolean add(Music music);

    /**
     * 获取音乐
     * 
     * @param author
     * @param title
     * @return
     */
    public List<Music> findByAuthorAndTitle(String author, String title);

    /**
     * 根据index查询music
     * 
     * @param indexed
     * @return
     */
    public List<Music> findMusicsByIndex(IndexEnum indexed);
}
