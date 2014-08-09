package com.zhy.listen.cache;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClient {

    private static JedisPool pool;

    private JedisClient() {

    }

    public static Jedis getInstance() {
        if (pool == null) {
            init();
        }
        return pool.getResource();
    }

    private static void init() {
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
    }
}
