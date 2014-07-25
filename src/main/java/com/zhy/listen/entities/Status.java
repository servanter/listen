package com.zhy.listen.entities;

import java.sql.Timestamp;

import com.zhy.listen.bean.Page;

/**
 * 状态
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class Status extends Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7077011134068664877L;

    private Long id;
    
    private Long userId;
    
    private String content;
    
    private Timestamp createTime;
    
    private Timestamp modifyTime;
    
    private Boolean isValid;

    
    public Status() {
        super();
    }

    
    public Status(Long userId, String content, Boolean isValid) {
        super();
        this.userId = userId;
        this.content = content;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.isValid = isValid;
    }

    

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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


    public Timestamp getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    public Timestamp getModifyTime() {
        return modifyTime;
    }


    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    public Boolean getIsValid() {
        return isValid;
    }


    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
        result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
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
        Status other = (Status) obj;
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
        if (modifyTime == null) {
            if (other.modifyTime != null)
                return false;
        } else if (!modifyTime.equals(other.modifyTime))
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
        return "Status [id=" + id + ", userId=" + userId + ", content=" + content + ", createTime=" + createTime + ", modifyTime=" + modifyTime
                + ", isValid=" + isValid + "]";
    }
    
    
}
