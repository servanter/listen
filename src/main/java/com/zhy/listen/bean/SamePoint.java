package com.zhy.listen.bean;

/**
 * 计算两个人相似得分详单
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public enum SamePoint {

    /**
     * 来自同一开放平台
     */
    SAMEFROM(0.1f),

    /**
     * 同一地点/同一城市
     */
    SAMEPATH(0.1f),

    /**
     * 相同标签
     */
    SAMETAG(0.1f),

    /**
     * 投过相同投票
     */
    VOTING(0.01f),

    /**
     * 是否互为好友
     */
    ISFRIEND(0.1f),

    /**
     * 是否有5个以上共同好友
     */
    HASFIVECOMMONFRIENDS(0.3f);

    private float point;

    private SamePoint(Float point) {
        this.point = point;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

}
