package com.zhy.listen.series;

import com.zhy.listen.bean.query.QueryResult;

public interface SeriesService {

    /**
     * 根据用户输入的字符串获取查询结果
     * 
     * @param text
     * @return
     */
    public QueryResult findMusicByText(String text);
}
