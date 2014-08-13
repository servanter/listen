package com.zhy.listen.entities;

import java.util.Date;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.SubType;

/**
 * 新鲜事
 * 
 * @author zhanghongyan
 * 
 */
public class FeedNews extends Page {

    /**
     * 
     */
    private static final long serialVersionUID = -4795227901773999250L;

    private Long id;

    private Long subNewsId;

    private Long userId;

    private String content;

    private String link;

    private Date createTime;

    private SubType subType;

    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubNewsId() {
        return subNewsId;
    }

    public void setSubNewsId(Long subNewsId) {
        this.subNewsId = subNewsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return "FeedNews [id=" + id + ", subNewsId=" + subNewsId + ", userId=" + userId + ", content=" + content
                + ", link=" + link + ", createTime=" + createTime + ", subType=" + subType + ", isValid=" + isValid
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((subNewsId == null) ? 0 : subNewsId.hashCode());
        result = prime * result + ((subType == null) ? 0 : subType.hashCode());
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
        FeedNews other = (FeedNews) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isValid == null) {
            if (other.isValid != null)
                return false;
        } else if (!isValid.equals(other.isValid))
            return false;
        if (link == null) {
            if (other.link != null)
                return false;
        } else if (!link.equals(other.link))
            return false;
        if (subNewsId == null) {
            if (other.subNewsId != null)
                return false;
        } else if (!subNewsId.equals(other.subNewsId))
            return false;
        if (subType != other.subType)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
