package com.zhy.listen.entities;

import java.sql.Timestamp;

import com.zhy.listen.bean.Page;

/**
 * 地点
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public class Path extends Page {

    /**
     * 
     */
    private static final long serialVersionUID = 97588790525316819L;

    private Long id;

    private Long userId;

    private String loc;

    private String province;

    private String city;

    /**
     * 用户是否清除位置信息
     */
    private Boolean isClean;
    
    /**
     * 出现时间
     */
    private Timestamp discoveryTime;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsClean() {
        return isClean;
    }

    public void setIsClean(Boolean isClean) {
        this.isClean = isClean;
    }


    
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
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
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((discoveryTime == null) ? 0 : discoveryTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isClean == null) ? 0 : isClean.hashCode());
        result = prime * result + ((loc == null) ? 0 : loc.hashCode());
        result = prime * result + ((province == null) ? 0 : province.hashCode());
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
        Path other = (Path) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (discoveryTime == null) {
            if (other.discoveryTime != null)
                return false;
        } else if (!discoveryTime.equals(other.discoveryTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        if (province == null) {
            if (other.province != null)
                return false;
        } else if (!province.equals(other.province))
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
        return "Path [id=" + id + ", userId=" + userId + ", loc=" + loc + ", province=" + province + ", city=" + city + ", isClean=" + isClean
                + ", discoveryTime=" + discoveryTime + "]";
    }

}
