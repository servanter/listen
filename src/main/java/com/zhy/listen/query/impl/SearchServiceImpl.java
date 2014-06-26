package com.zhy.listen.query.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.query.AbstractSearch;
import com.zhy.listen.query.SearchService;

@Service
public class SearchServiceImpl implements SearchService, ApplicationContextAware {

    private ApplicationContext applicationContext;
    
    @Override
    public <T extends Paging> List<T> search(T t) {
        String className = t.getClass().getSimpleName();
        String serviceName = String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1);
        Object service = applicationContext.getBean(serviceName + "Service");
        String parentName = service.getClass().getSuperclass().getName();
        List<T> result = ((AbstractSearch) service).search(t);
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

}
