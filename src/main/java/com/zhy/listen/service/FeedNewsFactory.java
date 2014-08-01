package com.zhy.listen.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.zhy.listen.bean.SubType;

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
}
