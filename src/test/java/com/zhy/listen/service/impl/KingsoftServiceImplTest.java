package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.entities.Music;
import com.zhy.listen.service.KingsoftService;


public class KingsoftServiceImplTest extends SuperTest {

    @Autowired
    private KingsoftService kingsoftService;
    
    @Test
    public void testUpload() {
//        String a;
//        try {
//            a = URLEncoder.encode("汪峰", "UTF-8");
//            System.out.println(a);
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Music music = new Music();
        music.setAuthor("林俊杰");
        music.setTitle("冻结");
        music.setUrl("http://zhangmenshiting.baidu.com/data2/music/64007314/64007314.mp3?xcode=b6601d8100f0ee2065c8ffe0b338d325813b03e70fc8799e&mid=0.79314244542418");
        kingsoftService.upload(music);
    }

}
