package com.zhy.listen.query.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.query.AbstractSearch;
import com.zhy.listen.query.SearchService;

@Service
public class SearchServiceImpl implements SearchService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    @Override
    public QueryResult search(Object t) {
        String className = t.getClass().getSimpleName();
        String serviceName = String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1);
        Object service = applicationContext.getBean(serviceName + "Service");
        QueryResult result = ((AbstractSearch) service).search(t);
        return result;
    }

}
