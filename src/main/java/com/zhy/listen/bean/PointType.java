package com.zhy.listen.bean;

public enum PointType {

    /**
     * 登陆
     */
    LOGIN(1, 2);

    private PointType(int type, int score) {
        this.type = type;
        this.score = score;
    }

    private int type;

    private int score;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static PointType type2PointType(int type) {
        PointType[] pointTypes = PointType.values();
        for (PointType pointType : pointTypes) {
            if (pointType.type == type) {
                return pointType;
            }
        }
        return null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
