package com.zhy.listen.cache;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.zhy.listen.common.Constant;
import com.zhy.listen.template.TemplateService;

/**
 * memcached util for set and get values.
 * 
 * @author zhy19890221@gmail.com
 * 
 */
@Component
public class Memcached {
    public static final long MINUTE = 60;
    public static final long HOUR = 60 * 60;
    public static final long DAY = 60 * 60 * 12;
    public static final long MONTH = 60 * 60 * 12 * 30;
    public static final long YEAR = 60 * 60 * 12 * 30 * 365;

    private MemCachedClient client = null;

    @Autowired
    private TemplateService templateService;

    /**
     * The pool must be initialized prior to use for memcached client.
     */

    @PostConstruct
    private void init() {
        String[] serverlist = templateService.getMessage(Constant.TEMPLATE_MEMCACHE_SERVER_WORDS).split(",");
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(serverlist);
        pool.initialize();
    }

    private Memcached() {
        client = new MemCachedClient();
    }

    /**
     * Get the instance of memcached client.
     */
    public synchronized static Memcached getInstance() {
        return new Memcached();
    }

    public boolean delete(String key) {
        return client.delete(key);
    }

    public boolean delete(String key, Date expiry) {
        return client.delete(key, expiry);
    }

    public boolean delete(String key, long seconds) {
        return client.delete(key, new Date(System.currentTimeMillis() + seconds * 1000));
    }

    public boolean set(String key, Object value) {
        return client.set(key, value, new Date(System.currentTimeMillis() + 2 * HOUR));
    }

    public boolean set(String key, Object value, Date expiry) {
        return client.set(key, value, expiry);
    }

    public boolean set(String key, Object value, long seconds) {
        return client.set(key, value, new Date(System.currentTimeMillis() + seconds * 1000));
    }

    public boolean add(String key, Object value) {
        return client.add(key, value, new Date(System.currentTimeMillis() + 2 * HOUR));
    }

    public boolean add(String key, Object value, Date expiry) {
        return client.add(key, value, expiry);
    }

    public boolean add(String key, Object value, long seconds) {
        return client.add(key, value, new Date(System.currentTimeMillis() + seconds * 1000));
    }

    public boolean replace(String key, Object value) {
        return client.replace(key, value, new Date(System.currentTimeMillis() + 2 * HOUR));
    }

    public boolean replace(String key, Object value, Date expiry) {
        return client.replace(key, value, expiry);
    }

    public boolean replace(String key, Object value, long seconds) {
        return client.replace(key, value, new Date(System.currentTimeMillis() + seconds * 1000));
    }

    public <T extends Object> T get(String key) {
        return (T) client.get(key);
    }

    public <T extends Object> Map<String, T> getMulti(String[] keys) {
        Map<String, Object> result = client.getMulti(keys);
        return (Map<String, T>) result;
    }
    
    public <T extends Object> T[] getMultiArray(String[] keys) {
        Object[] arr = client.getMultiArray(keys);
        return (T[]) arr;
    }
}
