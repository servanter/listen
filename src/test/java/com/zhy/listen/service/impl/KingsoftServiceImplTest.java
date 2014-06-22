package com.zhy.listen.service.impl;

import static org.junit.Assert.*;

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
        kingsoftService.upload(new Music());
    }

}
