package com.zhy.listen.util;

import com.zhy.listen.bean.FeedNewsCount;
import com.zhy.listen.entities.FeedNews;

public class BeanUtils {

    /**
     * FeedNews 转FeedNewsCount
     * @param f
     * @return
     */
    public static FeedNewsCount feedNews2FeedNewsCount (FeedNews f) {
        FeedNewsCount feedNewsCount = new FeedNewsCount();
        feedNewsCount.setId(f.getId());
        feedNewsCount.setSubNewsId(f.getSubNewsId());
        feedNewsCount.setUserId(f.getUserId());
        feedNewsCount.setContent(f.getContent());
        feedNewsCount.setLink(f.getLink());
        feedNewsCount.setCreateTime(f.getCreateTime());
        feedNewsCount.setSubType(f.getSubType());
        feedNewsCount.setIsValid(f.getIsValid());
        return feedNewsCount;
    }
}
