package com.zhy.listen.bean.indexer;

import com.zhy.listen.bean.UserStatusPoint;
import com.zhy.listen.entities.Music;
import com.zhy.listen.entities.Path;

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
    MUSIC("Music", Music.class.getPackage().getName()),

    /**
     * 用户相关
     */
    USER("UserStatusPoint", UserStatusPoint.class.getPackage().getName()),
    
    /**
     * 位置信息
     */
    PATH("Path", Path.class.getPackage().getName());

    private String alias;
    
    private String path;

    private IndexerClass(String alias, String path) {
        this.alias = alias;
        this.path = path;
    }

    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
