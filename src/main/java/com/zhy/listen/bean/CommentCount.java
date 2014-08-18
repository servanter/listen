package com.zhy.listen.bean;

/**
 *
 * @author zhanghongyan
 *
 */
public class CommentCount {

    private Long dependId;
    
    private Integer count;
    
    private SubType type;

    public SubType getType() {
        return type;
    }

    public void setType(SubType type) {
        this.type = type;
    }

    public Long getDependId() {
        return dependId;
    }

    public void setDependId(Long dependId) {
        this.dependId = dependId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CommentCount [dependId=" + dependId + ", count=" + count + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dependId == null) ? 0 : dependId.hashCode());
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
        CommentCount other = (CommentCount) obj;
        if (dependId == null) {
            if (other.dependId != null)
                return false;
        } else if (!dependId.equals(other.dependId))
            return false;
        return true;
    }
    
}
