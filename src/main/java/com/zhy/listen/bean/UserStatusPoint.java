package com.zhy.listen.bean;

/**
 *
 * @author zhanghongyan
 *
 */
/**
 *
 * @author zhanghongyan
 *
 */
public class UserStatusPoint extends UserStatus{

    /**
     * 
     */
    private static final long serialVersionUID = -4585790165574467396L;

    private Long point;
    
    private String honour;
 
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((honour == null) ? 0 : honour.hashCode());
        result = prime * result + ((point == null) ? 0 : point.hashCode());
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
        UserStatusPoint other = (UserStatusPoint) obj;
        if (honour == null) {
            if (other.honour != null)
                return false;
        } else if (!honour.equals(other.honour))
            return false;
        if (point == null) {
            if (other.point != null)
                return false;
        } else if (!point.equals(other.point))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserStatusPoint [point=" + point + ", honour=" + honour + "]";
    }
    
    
}
