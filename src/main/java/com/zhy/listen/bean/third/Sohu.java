package com.zhy.listen.bean.third;

import com.zhy.listen.bean.Src;
import com.zhy.listen.bean.Third;


public class Sohu extends Third {

    /**
     * 
     */
    private static final long serialVersionUID = -5104605985928201860L;

    public Sohu() {
        this.src = Src.SOHU;
    }
    
    public void setCode(String code){
        this.metaIndex1 = code;
    }
    
    public String getCode(){
        return metaIndex1;
    }
}
