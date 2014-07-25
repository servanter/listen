package com.zhy.listen.bean;

/**
 * 积分配置
 * 
 * @author zhanghongyan@outlook.com
 *
 */
public class PointConfig implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5794476033838215276L;

    private Long id;
    
    private Long minPoint;
    
    private Long maxPoint;
    
    /**
     * 级别
     */
    private Integer level;
    
    /**
     * 头衔
     */
    private String honour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(Long minPoint) {
        this.minPoint = minPoint;
    }

    public Long getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Long maxPoint) {
        this.maxPoint = maxPoint;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getHonour() {
        return honour;
    }

    public void setHonour(String honour) {
        this.honour = honour;
    }
    
}
