package com.zhy.listen.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * fieldutil
 * @author zhanghongyan
 *
 */
public class FieldUtils {

    
    /**
     * 获取 solr 中doc的fieldname
     * @param field
     * @return
     */
    public static String getDocField(Field field) {
        String fieldName = field.getName();
        String result = fieldName;
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(fieldName);
        while(matcher.find()) {
            result = result.substring(0, result.indexOf(matcher.group())) + "_" + matcher.group().toLowerCase() + result.substring(result.indexOf(matcher.group()) + 1);
        }
        return result;
    }
}
