package com.zhy.listen.template.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zhy.listen.template.TemplateService;


/**
 * 模板工具类
 * 
 * @author zhy
 * 
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    public static Logger logger = Logger.getLogger(TemplateServiceImpl.class);

    /**
     * 模板
     */
    private static Properties properties;

    @PostConstruct
    private synchronized void init() {
        if (properties == null) {
            try {
                logger.info("[TemplateService]: Loading template.properties....");
                properties = new Properties();
                InputStream inputStream = TemplateServiceImpl.class.getClassLoader().getResourceAsStream(
                        "template.properties");
                properties.load(new InputStreamReader(inputStream, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回模板信息
     * 
     * @param key
     * @param args
     * @return
     */
    public String getMessage(String key, String... args) {
        String result = "";
        if (properties.containsKey(key)) {
            result = properties.getProperty(key);
        }
        if (args != null && args.length > 0) {
            return MessageFormat.format(result, args);
        }
        return result;
    }

    @Override
    public boolean contains(String key) {
        return properties.contains(key);
    }

}
