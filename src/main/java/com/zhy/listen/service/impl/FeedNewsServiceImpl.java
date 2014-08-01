package com.zhy.listen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.dao.FeedNewsDAO;
import com.zhy.listen.entities.FeedNews;
import com.zhy.listen.entities.Status;
import com.zhy.listen.service.FeedNewsFactory;
import com.zhy.listen.service.FeedNewsService;

@Service
public class FeedNewsServiceImpl implements FeedNewsService {

    @Autowired
    private FeedNewsDAO feedNewsDAO;
    
    @Autowired
    private FeedNewsFactory feedNewsFactory;

    @Override
    public Paging<FeedNews> findByNews(FeedNews feedNews) {
        return feedNewsDAO.getByNews(feedNews);
    }

    @Override
    public <T extends Object> T findDetailByIdAndType(Long id, SubType type) {
        return (T) feedNewsFactory.generateService(type).findById(id);
    }

    @Override
    public boolean add(Page page, SubType type) {
        FeedNews feedNews = generateFeedNews(page, type);
        return feedNewsDAO.save(feedNews) > 0;
    }
    
    private FeedNews generateFeedNews(Page page, SubType type) {
        if(type == SubType.STATUS) {
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
