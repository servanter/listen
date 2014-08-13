package com.zhy.listen.cache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.EnumMorpher;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Component;

import com.zhy.listen.bean.SubType;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisClient {

    public JedisPool pool;

    public Jedis jedis;

    @PostConstruct
    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        if (bundle == null) {
            throw new IllegalArgumentException("[redis.properties] is not found!");
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxTotal").trim()));
        config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle").trim()));
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait").trim()));
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow").trim()));
        config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn").trim()));
        pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port").trim()));
        jedis = pool.getResource();
    }

    public <T extends Object> List<T> lrange(String key, long start, long end) {
        List<String> values = jedis.lrange(key, start, end);
        JsonConfig config = new JsonConfig();
        String[] dateFmts = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
        JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(dateFmts));
        if (values != null && values.size() > 0) {
            JSONArray array = JSONArray.fromObject(values);
            return array.toList(array, Long.class);
        }
        return null;
    }
    
    public <T extends Object> List<T> lrange(String key, long start, long end, Class<T> t) {
        List<String> values = jedis.lrange(key, start, end);
        JsonConfig config = new JsonConfig();
        String[] dateFmts = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
        JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(dateFmts));
        JSONUtils.getMorpherRegistry().registerMorpher(new EnumMorpher(SubType.class));
        if (values != null && values.size() > 0) {
            JSONArray array = JSONArray.fromObject(values);
            if(t.getSimpleName().equals("Long")) {
                return array.toList(array, Long.class);
            } else if(t.getSimpleName().equals("Integer")) {
                return array.toList(array, Integer.class);
            } else {
                try {
                    return array.toList(array, t.newInstance(), config);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Long lpush(String key, Object value) {
        JsonConfig config = new JsonConfig();
        String val = JSONObject.fromObject(value, config).toString();
        Long count = jedis.lpush(key, val);
        return count;
    }
    
    public Long lpush(String key, List<? extends Object> values) {
        Long count = 0L;
        String str[] = null;
        JsonConfig config = new JsonConfig();
        if (values != null && values.size() > 0) {
            str = new String[values.size()];
            for (int i = 0; i < values.size(); i++) {
                Object val = values.get(i);
                if (val instanceof Integer || val instanceof Long) {
                    str[i] = String.valueOf(val);
                } else {
                    str[i] = JSONObject.fromObject(values.get(i), config).toString();
                }
            }
            count = jedis.lpush(key, str);
        }
        return count;
    }
    
    public Long rpush(String key, Object value) {
        JsonConfig config = new JsonConfig();
        String val = JSONObject.fromObject(value, config).toString();
        Long count = jedis.rpush(key, val);
        return count;
    }
    
    public Long rpush(String key, List<? extends Object> values) {
        Long count = 0L;
        String str[] = null;
        JsonConfig config = new JsonConfig();
        if (values != null && values.size() > 0) {
            str = new String[values.size()];
            for (int i = 0; i < values.size(); i++) {
                Object val = values.get(i);
                if (val instanceof Integer || val instanceof Long) {
                    str[i] = String.valueOf(val);
                } else {
                    str[i] = JSONObject.fromObject(values.get(i), config).toString();
                }
            }
            count = jedis.rpush(key, str);
        }
        return count;
    }

    public Long lrem(String key, long count, Object o) {
        String str = "";
        if(o instanceof List) {
            str = JSONArray.fromObject(o).toString();
        } else {
            str = JSONObject.fromObject(o).toString();
        }
        return jedis.lrem(key, count, str);
    }
}
