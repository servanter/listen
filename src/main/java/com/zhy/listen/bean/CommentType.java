package com.zhy.listen.bean;

/**
 * 通用类型,如评论中可以评论投票评论,状态评论
 * @author zhy19890221@gmail.com
 *
 */
public enum CommentType {

    /**
     * 状态评论
     */
    STATUS(1);

    private int type;

    private CommentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static CommentType type2CommonType(int i) {
        CommentType[] commonTypes = CommentType.values();
        for (CommentType commonType : commonTypes) {
            if (commonType.type == i) {
                return commonType;
            }
        }
        return null;
    }

}
