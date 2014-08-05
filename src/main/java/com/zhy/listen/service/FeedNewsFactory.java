package com.zhy.listen.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;

@Component
public class FeedNewsFactory implements ApplicationContextAware{

    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
    
    public SubNewsService generateService(SubType subType) {
        String alias = subType.name().toLowerCase() + "Service";
        SubNewsService subNewsService = (SubNewsService) applicationContext.getBean(alias);
        return subNewsService;
    }
    
    public FeedNews generateFeedNews(Page page, SubType type) {
        if (type == SubType.STATUS) {
            Status status = (Status) page;
            FeedNews feedNews = new FeedNews();
            feedNews.setSubNewsId(status.getId());
            feedNews.setUserId(status.getUserId());
            feedNews.setContent(status.getContent());
            feedNews.setSubType(SubType.STATUS);
            feedNews.setCreateTime(status.getCreateTime());
            return feedNews;
        }
        return null;
    }
}
