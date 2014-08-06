package com.zhy.listen.bean;

/**
 * 错误代码
 * 
 * @author zhanghongyan
 * 
 */
public enum ErrorCode {

    SUCCESS(10000, "成功"),
    
    USER_NOT_FOUNT(20001, "用户没有找到"),
    
    ERROR(99999, "系统错误");

    private int code;

    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
