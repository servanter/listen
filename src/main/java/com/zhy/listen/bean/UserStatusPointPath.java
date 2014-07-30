package com.zhy.listen.bean;

import java.sql.Timestamp;

import com.zhy.listen.entities.User;

/**
 * 用户状态、积分、地理位置bean
 *
 * @author zhanghongyan
 *
 */
public class UserStatusPointPath extends User {

    /**
     * 
     */
    private static final long serialVersionUID = -1896139545616764016L;

    private String content;

    private Timestamp statusTime;
    
    private Long point;
    
    private String honour;
    
    private String loc;

    private String discoveryProvince;
    
    private String discoveryCity;
    
    private Boolean isClean;
    
    private Timestamp discoveryTime;

    
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

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getHonour() {
        return honour;
    }

    public void setHonour(String honour) {
        this.honour = honour;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getDiscoveryProvince() {
        return discoveryProvince;
    }

    public void setDiscoveryProvince(String discoveryProvince) {
        this.discoveryProvince = discoveryProvince;
    }

    public String getDiscoveryCity() {
        return discoveryCity;
    }

    public void setDiscoveryCity(String discoveryCity) {
        this.discoveryCity = discoveryCity;
    }

    public Boolean getIsClean() {
        return isClean;
    }

    public void setIsClean(Boolean isClean) {
        this.isClean = isClean;
    }

    public Timestamp getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(Timestamp discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((discoveryCity == null) ? 0 : discoveryCity.hashCode());
        result = prime * result + ((discoveryProvince == null) ? 0 : discoveryProvince.hashCode());
        result = prime * result + ((discoveryTime == null) ? 0 : discoveryTime.hashCode());
        result = prime * result + ((honour == null) ? 0 : honour.hashCode());
        result = prime * result + ((isClean == null) ? 0 : isClean.hashCode());
        result = prime * result + ((loc == null) ? 0 : loc.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        UserStatusPointPath other = (UserStatusPointPath) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (discoveryCity == null) {
            if (other.discoveryCity != null)
                return false;
        } else if (!discoveryCity.equals(other.discoveryCity))
            return false;
        if (discoveryProvince == null) {
            if (other.discoveryProvince != null)
                return false;
        } else if (!discoveryProvince.equals(other.discoveryProvince))
            return false;
        if (discoveryTime == null) {
            if (other.discoveryTime != null)
                return false;
        } else if (!discoveryTime.equals(other.discoveryTime))
            return false;
        if (honour == null) {
            if (other.honour != null)
                return false;
        } else if (!honour.equals(other.honour))
            return false;
        if (isClean == null) {
            if (other.isClean != null)
                return false;
        } else if (!isClean.equals(other.isClean))
            return false;
        if (loc == null) {
            if (other.loc != null)
                return false;
        } else if (!loc.equals(other.loc))
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
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
        return "UserStatusPointPath [content=" + content + ", statusTime=" + statusTime + ", point=" + point
                + ", honour=" + honour + ", loc=" + loc + ", discoveryProvince=" + discoveryProvince
                + ", discoveryCity=" + discoveryCity + ", isClean=" + isClean + ", discoveryTime=" + discoveryTime
                + "]";
    }
    
    
    
}
