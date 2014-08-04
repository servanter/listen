package com.zhy.listen.cache;

/**
 * 缓存key规则
 * 
 * @author zhanghongyan
 * 
 */
public class KeyGenerator {

    public static final String SPLIT = "|";

    public static String generateKey(String prefix, Object Object) {
        return prefix + SPLIT + String.valueOf(Object);
    }

    public static String getRowObject(String key, String prefix) {
        String str = key;
        String arr[] = str.split("\\" + SPLIT);
        if (arr.length == 2) {
            return arr[1];
        }
        return null;
    }
}
