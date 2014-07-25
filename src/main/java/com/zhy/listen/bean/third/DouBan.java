package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.entities.Third;


/**
 * 豆瓣
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public class DouBan extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = 1750938710120108704L;

    public DouBan() {
        this.src = Src.DOUBAN;
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
