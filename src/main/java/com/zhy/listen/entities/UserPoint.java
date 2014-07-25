package com.zhy.listen.entities;

import java.sql.Timestamp;

/**
 * 用户积分
 * 
 * @author zhanghongyan
 * 
 */
public class UserPoint implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8903209420126685331L;

    private Long id;

    private Long userId;

    private Long point;

    private String userHonour;

    private Timestamp createTime;

    private Timestamp modifyTime;

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

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getUserHonour() {
        return userHonour;
    }

    public void setUserHonour(String userHonour) {
        this.userHonour = userHonour;
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

    @Override
    public String toString() {
        return "UserPoint [id=" + id + ", userId=" + userId + ", point=" + point + ", userHonour=" + userHonour
                + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        result = prime * result + ((userHonour == null) ? 0 : userHonour.hashCode());
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
        UserPoint other = (UserPoint) obj;
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
        if (modifyTime == null) {
            if (other.modifyTime != null)
                return false;
        } else if (!modifyTime.equals(other.modifyTime))
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        if (userHonour == null) {
            if (other.userHonour != null)
                return false;
        } else if (!userHonour.equals(other.userHonour))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
