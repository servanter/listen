package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.entities.Third;


/**
 * �?心网
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class KaiXin extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = -2867754218059735586L;

    public KaiXin() {
        this.src = Src.KAIXIN;
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
