package com.zhy.listen.bean.indexer;

import java.sql.Timestamp;
import java.util.List;

public class Indexer {

    /**
     * 待索引类别
     */
    private IndexerClass indexerClass;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 待创建索引的数据
     */
    private List<?> needIndexList;

    public Indexer() {
    }

    public Indexer(List<?> needIndexList, IndexerClass indexerClass) {
        this.needIndexList = needIndexList;
        this.indexerClass = indexerClass;
        createTime = new Timestamp(System.currentTimeMillis());
    }

    public IndexerClass getIndexerClass() {
        return indexerClass;
    }

    public void setIndexerClass(IndexerClass indexerClass) {
        this.indexerClass = indexerClass;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<?> getNeedIndexList() {
        return needIndexList;
    }

    public void setNeedIndexList(List<?> needIndexList) {
        this.needIndexList = needIndexList;
    }

}
