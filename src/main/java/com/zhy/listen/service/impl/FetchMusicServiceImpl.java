package com.zhy.listen.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.common.Constant;
import com.zhy.listen.service.FetchMusicService;
import com.zhy.listen.template.TemplateService;

@Service
public class FetchMusicServiceImpl implements FetchMusicService {

    @Autowired
    private TemplateService templateService;

    @Override
    public Music findMusicByBaidu(String author, String topic) throws Exception {
        Music result = new Music(author, topic);
        String url = "";
        if(author == null || author.length() == 0) {
            url = templateService.getMessage(Constant.BAIDU_MUSIC_URL, topic, author);
        } else {
            url = templateService.getMessage(Constant.BAIDU_MUSIC_NO_AUTHOR_URL, topic);
        }
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByTag("count");
        if(elements != null && elements.size() > 0 && Integer.parseInt(elements.get(0).text()) > 0) {
            Elements elements1 = document.getElementsByTag("encode");
            Elements elements2 = document.getElementsByTag("decode");
            Elements elements3 = document.getElementsByTag("lrcid");
            if (elements1 != null && elements1.size() > 0 && elements2 != null && elements2.size() > 0) {
                for (int i = 0; i < elements1.size(); i++) {
                    String first = elements1.get(i).text();
                    String downloadUrl = "";
                    String lrc = "";
                    if (first.contains("/")) {
                        downloadUrl = first.substring(0, first.lastIndexOf("/") + 1) + elements2.get(i).text();
                        result.setUrl(downloadUrl);
                    }
                    if (elements3 != null && elements3.size() > 0 && !elements3.text().equals("0")) {
                        String rowLrc = elements3.get(0).text();
                        lrc = templateService.getMessage(Constant.BAIDU_MUSIC_LRC_URL, String.valueOf(Integer.parseInt(rowLrc) / 100), rowLrc);
                        result.setLrc(lrc);
                    }
                    if (downloadUrl != null && downloadUrl.length() > 0 && lrc != null && lrc.length() > 0) {
                        break;
                    }
                }
            }
        }
        return result;
    }

}
