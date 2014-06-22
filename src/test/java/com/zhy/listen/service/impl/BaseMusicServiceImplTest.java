package com.zhy.listen.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.service.BaseMusicService;

public class BaseMusicServiceImplTest extends SuperTest{

    @Autowired
    private BaseMusicService baseMusicService;
    
    @Test
    public void testAdd() {
        Music music = new Music();
        music.setTitle("回到过去");
        music.setAuthor("周杰伦");
        music.setUrl("www.baodu.com");
        music.setLrc("想回到过去");
        Assert.assertTrue("Can't save the music ?", baseMusicService.add(music));
        List<Music> musics = baseMusicService.findNotUploadMusics();
        Assert.assertTrue("Can't find the musics ?", musics != null && musics.size() > 0);
    }

}
