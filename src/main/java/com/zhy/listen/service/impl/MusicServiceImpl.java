package com.zhy.listen.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.zhy.listen.bean.Music;
import com.zhy.listen.service.MusicService;

public class MusicServiceImpl implements MusicService {

    @Override
    public Music findMusicByBaidu(String author, String topic) throws Exception {
        GetMethod method = new GetMethod("http://box.zhangmen.baidu.com/x?op=12&count=1&title=" + topic + "$$" + author + "$$$$");
        HttpClient client = new HttpClient();
        client.executeMethod(method);
        String result = method.getResponseBodyAsString();
        return null;
    }

}
