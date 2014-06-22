package com.zhy.listen.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.service.MusicService;

public class MusicServiceImplTest extends SuperTest {

    @Autowired
    private MusicService musicService;
    
    @Test
    public void testFindMusicByBaidu() {
        try {
           Music music =  musicService.findMusicByBaidu("张宇", "雨一直下");
           Assert.assertNotNull("Can't find the music ?", music.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
