package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

import java.net.URLDecoder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Music;
import com.zhy.listen.service.KingsoftService;


public class KingsoftServiceImplTest extends SuperTest {

    @Autowired
    private KingsoftService kingsoftService;
    
    @Test
    public void testUpload() {
//        String a = URLDecoder.decode("%252F");
//        System.out.println(a);
        Music music = new Music();
        music.setUrl("http://zhangmenshiting2.baidu.com/data2/music/35420302/35420302.mp3?xcode=14425ed4171b44c643eb9f0766c34212598f0faef8e9c637&mid=0.86962648680245");
        kingsoftService.upload(music);
    }

}
