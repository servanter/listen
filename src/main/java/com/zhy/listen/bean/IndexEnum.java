package com.zhy.listen.bean;

/**
 * 索引枚举
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public enum IndexEnum {

    INDEXED(1),

    NOT_INDEXED(0);

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private IndexEnum(int type) {
        this.type = type;
    }
}
