package com.zhy.listen.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.service.MusicService;

public class MusicServiceImplTest extends SuperTest{

    @Autowired
    private MusicService musicService;
    
    @Test
    public void testAdd() {
        Music music = new Music();
        music.setTitle("回到过去");
        music.setAuthor("周杰伦");
        music.setUrl("www.baodu.com");
        music.setLrc("想回到过去");
        Assert.assertTrue("Can't save the music ?", musicService.add(music));
        List<Music> musics = musicService.findNotUploadMusics();
        Assert.assertTrue("Can't find the musics ?", musics != null && musics.size() > 0);
    }
    
    @Test
    public void testFind() {
        String author = "周杰伦";
        String title = "回到过去";
        List<Music> ms = musicService.findByAuthorAndTitle(author, title);
        Assert.assertTrue("Can't find the music.", ms != null && ms.size() > 0);
    }

}
