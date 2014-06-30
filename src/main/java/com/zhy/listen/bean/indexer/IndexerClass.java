package com.zhy.listen.bean.indexer;

/**
 * 索引类别
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public enum IndexerClass {

    /**
     * 音乐
     */
    MUSIC("Music"),

    /**
     * 用户相关
     */
    USER("User");

    private String alias;

    private IndexerClass(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
