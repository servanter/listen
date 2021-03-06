package com.zhy.listen.bean.query;

import java.sql.Timestamp;
import java.util.List;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.indexer.IndexerClass;

/**
 * 查询记录
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class QueryResult extends Page {

    /**
     * 
     */
    private static final long serialVersionUID = 264499318574907993L;

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

    private String keywords;

    /**
     * lucene命中条数(查询是作为查询条件)
     */
    private int hitCount;

    /**
     * 查询结果
     */
    private List<? extends Page> result;

    /**
     * 查询类型
     */
    private IndexerClass indexerClass;

    /**
     * 查询条件
     */
    private String rawQuery;

    /**
     * 返回信息<br>
     * 如果有查询结果, "耗时xxx秒,查询xxx条"<br>
     * 如果没有返回结果,"没有相关内容"
     * 
     */
    private String message;
    
    private ErrorCode errorCode; 

    public QueryResult() {

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

    public List<? extends Page> getResult() {
        return result;
    }

    public void setResult(List<? extends Page> result) {
        this.result = result;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRawQuery() {
        return rawQuery;
    }

    public void setRawQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
        result = prime * result + hitCount;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((indexerClass == null) ? 0 : indexerClass.hashCode());
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((queryFields == null) ? 0 : queryFields.hashCode());
        result = prime * result + ((queryTime == null) ? 0 : queryTime.hashCode());
        result = prime * result + ((rawQuery == null) ? 0 : rawQuery.hashCode());
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryResult other = (QueryResult) obj;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (errorCode != other.errorCode)
            return false;
        if (hitCount != other.hitCount)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (indexerClass != other.indexerClass)
            return false;
        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (queryFields == null) {
            if (other.queryFields != null)
                return false;
        } else if (!queryFields.equals(other.queryFields))
            return false;
        if (queryTime == null) {
            if (other.queryTime != null)
                return false;
        } else if (!queryTime.equals(other.queryTime))
            return false;
        if (rawQuery == null) {
            if (other.rawQuery != null)
                return false;
        } else if (!rawQuery.equals(other.rawQuery))
            return false;
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "QueryResult [id=" + id + ", queryFields=" + queryFields + ", queryTime=" + queryTime + ", endTime=" + endTime + ", userId=" + userId
                + ", keywords=" + keywords + ", hitCount=" + hitCount + ", result=" + result + ", indexerClass=" + indexerClass + ", rawQuery="
                + rawQuery + ", message=" + message + ", errorCode=" + errorCode + "]";
    }

    /**
     * 根据field name获取value
     * 
     * @param name
     * @return
     */
    public String getQueryFieldValue(String name) {
        String result = null;
        for (QueryField field : getQueryFields()) {
            if (field.getFieldName().equals(name)) {
                result = field.getValue();
                break;
            }
        }
        return result;
    }
}
