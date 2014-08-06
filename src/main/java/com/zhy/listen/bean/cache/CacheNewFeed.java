package com.zhy.listen.bean.cache;

import java.sql.Timestamp;

/**
 * 最新新鲜事缓存
 * 
 * @author zhanghongyan
 * 
 */
public class CacheNewFeed implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6526396216971078621L;

    private Long newId;

    private Timestamp createTime;

    public Long getNewId() {
        return newId;
    }

    public void setNewId(Long newId) {
        this.newId = newId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CacheNewFeed [newId=" + newId + ", createTime=" + createTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((newId == null) ? 0 : newId.hashCode());
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
        CacheNewFeed other = (CacheNewFeed) obj;
        if (newId == null) {
            if (other.newId != null)
                return false;
        } else if (!newId.equals(other.newId))
            return false;
        return true;
    }

}
