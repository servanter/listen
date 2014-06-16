package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;


/**
 * 人人�?
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class RenRen extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = 1258447494950968865L;

    public RenRen() {
        this.src = Src.RENREN;
    }

    public void setCode(String code) {
        this.metaIndex1 = code;
    }

    public String getCode() {
        return metaIndex1;
    }

    public void setToken(String token) {
        metaIndex2 = token;
    }

    public String getToken() {
        return metaIndex2;
    }
}
