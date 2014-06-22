package com.zhy.listen.bean;

public enum MusicUploadEnum {

    UPLOADED(1),
    
    NOT_UPLOADED(0);
    
    private int type;
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private MusicUploadEnum(int type) {
        this.type = type;
    }
}

