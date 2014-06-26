package com.zhy.listen.bean.query;

/**
 * 查询字段
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class QueryField {

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 查询值 以空格分开,生成termQuery时,生成多个
     */
    private String fieldExcept;

    public QueryField() {

    }

    public QueryField(String fieldName, String fieldExcept) {
        this.fieldName = fieldName;
        this.fieldExcept = fieldExcept;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldExcept() {
        return fieldExcept;
    }

    public void setFieldExcept(String fieldExcept) {
        this.fieldExcept = fieldExcept;
    }
}
