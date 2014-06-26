package com.zhy.listen.bean.query;

import java.sql.Timestamp;
import java.util.List;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.indexer.IndexerClass;

/**
 * 查询记录
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class QueryResult {

    private Long id;

    /**
     * 查询字段
     */
    private List<QueryField> queryFields;

    /**
     * 查询时间
     */
    private Timestamp queryTime = new Timestamp(System.currentTimeMillis());

    /**
     * 结束时间(结束-查询=耗时)
     */
    private Timestamp endTime;

    /**
     * 查询用户ID
     */
    private Long userId;

    /**
     * 查询条数 默认20
     */
    private int count = 20;

    /**
     * lucene命中条数(查询是作为查询条件)
     */
    private int hitCount;

    /**
     * 查询结果
     */
    private List<? extends Paging> result;

    /**
     * 查询类型
     */
    private IndexerClass indexerClass;

    /**
     * 返回信息<br>
     * 如果有查询结果, "耗时xxx秒,查询xxx条"<br>
     * 如果没有返回结果,"没有相关内容"
     * 
     */
    private String message;

    public QueryResult() {

    }

    public QueryResult(Long userId, IndexerClass indexerClass, List<QueryField> queryFields) {
        this.userId = userId;
        this.indexerClass = indexerClass;
        this.queryFields = queryFields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Timestamp queryTime) {
        this.queryTime = queryTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<? extends Paging> getResult() {
        return result;
    }

    public void setResult(List<? extends Paging> result) {
        this.result = result;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IndexerClass getIndexerClass() {
        return indexerClass;
    }

    public void setIndexerClass(IndexerClass indexerClass) {
        this.indexerClass = indexerClass;
    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
    }

}
