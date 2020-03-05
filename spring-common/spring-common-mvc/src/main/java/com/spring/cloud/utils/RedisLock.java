package com.spring.cloud.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 加锁
     *
     * @param key        被秒杀商品的id
     * @param requestId  解锁时需要传递相等的值
     * @param expireTime 超时时间 毫秒
     * @return
     */
    public boolean lock(String key, String requestId, long expireTime) {
        String execute = redisTemplate.execute((RedisConnection o) -> {
            JedisCommands jedisCommands = (JedisCommands) o.getNativeConnection();
            return jedisCommands.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        });
        //如果key值不存在，则返回 true，且设置 value
        if (LOCK_SUCCESS.equals(execute)) {
            return true;
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param requestId
     */
    public void unlock(String key, String requestId) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

            DefaultRedisScript<String> rs = new DefaultRedisScript<String>();
            rs.setScriptText(script);
            rs.setResultType(String.class);
            RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
            List<String> keys = new ArrayList<>();
            keys.add(key);
            redisTemplate.execute(rs, stringRedisSerializer, stringRedisSerializer, keys, requestId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
