package com.zhy.listen.bean;

public enum SubType {

    NULL(0),

    /**
     * 状态
     */
    STATUS(1);

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private SubType(int type) {
        this.type = type;
    }

    public static SubType getSubFromType(int type) {
        SubType[] types = SubType.values();
        for (SubType s : types) {
            if (s.getType() == type) {
                return s;
            }
        }
        return SubType.NULL;
    }

}
