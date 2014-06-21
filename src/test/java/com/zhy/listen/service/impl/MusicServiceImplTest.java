package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.service.MusicService;

public class MusicServiceImplTest extends SuperTest {

    @Autowired
    private MusicService musicService;
    
    @Test
    public void testFindMusicByBaidu() {
        try {
            musicService.findMusicByBaidu("周杰伦", "火车叨位去");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
