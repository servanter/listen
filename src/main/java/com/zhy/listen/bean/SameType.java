package com.zhy.listen.bean;

/**
 * 感兴趣的类型
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public enum SameType {

    /**
     * 同一开放平台
     */
    FROM(1),

    /**
     * 同一地点/同一城市
     */
    PATH(2),

    /**
     * 标签
     */
    TAG(3),

    /**
     * 相同投票
     */
    VOTING(4),

    /**
     * 间接好友
     */
    INDIRECTFRIEND(5),
    
    /**
     * 所有
     */
    ALL(6);

    private int type;

    private SameType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static SameType code2SameType(int code) {
        SameType[] types = SameType.values();
        for (SameType type : types) {
            if (type.getType() == code) {
                return type;
            }
        }
        return null;
    }
}
