package com.zhy.listen.create;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.indexer.IndexerClass;

@Service
public class GenerateIndexServiceAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public GenerateIndexService getCreateIndexImpl(IndexerClass indexerClass) {
        try {
            return (GenerateIndexService) applicationContext.getBean("create" + indexerClass.getAlias() + "IndexServiceImpl");
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
