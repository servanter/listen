package com.zhy.listen.util;

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
public class JsonUtils {

    /**
     * date转json格式
     * 
     * @author zhanghongyan
     *
     */
    public static class DateProcesser implements JsonValueProcessor {

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
