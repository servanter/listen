package com.zhy.listen.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json工具类
 * 
 * @author zhanghongyan
 * 
 */
public abstract class JsonUtils implements JsonValueProcessor {

    public static JsonValueProcessor create(Class<?> o) {
        if (o == Date.class || o == Timestamp.class) {
            JsonUtils.DateProcesser dateProcesser = new DateProcesser("yyyy-MM-dd HH:mm:ss");
            return dateProcesser;
        }
        return null;
    }

    static class DateProcesser implements JsonValueProcessor {

        static String format;

        public DateProcesser(String format) {
            DateProcesser.format = format;
        }

        @Override
        public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
            if (arg1 instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(arg1);
            }
            return null;
        }

        @Override
        public Object processArrayValue(Object arg0, JsonConfig arg1) {
            if (arg0 instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(arg1);
            }
            return null;
        }

    }

}
