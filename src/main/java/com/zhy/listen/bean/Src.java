package com.zhy.listen.bean;

/**
 * �?放平台来�?
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public enum Src {

    SINA(1, "新浪微博", "sinaService"),

    QQWEIBO(2, "腾讯微博", "qqWeiboSerivce"),

    RENREN(3, "人人�?", "renrenService"),

    KAIXIN(4, "�?心网", "kaiXinService"),

    DOUBAN(5, "豆瓣�?", "douBanService"),

    WANGYI(6, "网易微博", "wangYiService"),

    QQ(7, "QQ", "qQService"),

    SOHU(8, "搜狐微博", "soHuService");

    private int code;

    private String description;

    private String serviceName;

    private Src(int code, String description, String serviceName) {
        this.code = code;
        this.description = description;
        this.serviceName = serviceName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Src code2Src(int code) {
        for (Src src : Src.values()) {
            if (src.getCode() == code) {
                return src;
            }
        }
        return null;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

}
