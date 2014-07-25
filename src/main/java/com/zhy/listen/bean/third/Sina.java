package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.entities.Third;


/**
 * 新浪微博
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class Sina extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = 4637558173496482697L;

    public Sina() {
        this.src = Src.SINA;
    }

    public void setCode(String code) {
        metaIndex1 = code;
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
