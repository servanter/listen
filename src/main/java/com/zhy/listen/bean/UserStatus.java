package com.zhy.listen.bean;

import java.sql.Timestamp;

import com.zhy.listen.entities.User;

/**
 * 用户基本信息及状态信息
 *
 * @author zhanghongyan@outlook.com
 *
 */
/**
 *
 * @author zhanghongyan@outlook.com
 *
 */
public class UserStatus extends User{

    /**
     * 
     */
    private static final long serialVersionUID = 4191130395234544985L;
    
    private String content;

    private Timestamp statusTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((statusTime == null) ? 0 : statusTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserStatus other = (UserStatus) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (statusTime == null) {
            if (other.statusTime != null)
                return false;
        } else if (!statusTime.equals(other.statusTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserStatus [content=" + content + ", statusTime=" + statusTime + "]";
    }
    
}
