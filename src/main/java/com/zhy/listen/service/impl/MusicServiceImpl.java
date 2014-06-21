package com.zhy.listen.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.common.Constant;
import com.zhy.listen.service.MusicService;
import com.zhy.listen.template.TemplateService;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private TemplateService templateService;
    
    @Override
    public Music findMusicByBaidu(String author, String topic) throws Exception {
        GetMethod method = new GetMethod(templateService.getMessage(Constant.BAIDU_MUSIC_URL, author, topic));
        HttpClient client = new HttpClient();
        client.executeMethod(method);
        String result = method.getResponseBodyAsString();
        return null;
    }

}
