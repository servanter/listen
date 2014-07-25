package com.zhy.listen.entities;

import java.sql.Timestamp;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.PointType;

/**
 * 积分详情
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public class PointDetail extends Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5925776682175926225L;

    private Long id;

    private Long userId;

    private PointType pointType;

    private Integer point;

    private Timestamp createTime;

    public PointDetail() {

    }

    public PointDetail(Long userId, PointType pointType) {
        this.userId = userId;
        this.pointType = pointType;
        this.point = pointType.getScore();
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

}
