package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;


public class WangYi extends Third{

    /**
     * 
     */
    private static final long serialVersionUID = -2061033197180497148L;

    public WangYi() {
        this.src = Src.WANGYI;
    }
    
    public void setCode(String code){
        this.metaIndex1 = code;
    }
    
    public String getCode(){
        return metaIndex1;
    }
    
    public void setToken(String token) {
        metaIndex2 = token;
    }

    public String getToken() {
        return metaIndex2;
    }
}
