package com.zhy.listen.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.entities.Music;
import com.zhy.listen.service.FetchMusicService;

public class FetchMusicServiceImplTest extends SuperTest {

    @Autowired
    private FetchMusicService fetchMusicService;
    
    @Test
    public void testFindMusicByBaidu() {
        try {
           Music music =  fetchMusicService.findMusicByBaidu("张宇", "雨一直下");
           Assert.assertNotNull("Can't find the music ?", music.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
