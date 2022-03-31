package com.grab.degree.activity.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * 客户端，通过这个操作redis集群
 *
 * @author yjlan
 */

public class ShardJedisClient implements IShardJedisClient {
    
    private final ShardJedisManager shardJedisManager;
    
    public ShardJedisClient(ShardJedisManager shardJedisManager) {
        this.shardJedisManager = shardJedisManager;
    }
    
    
    @Override
    public int getRedisCount() {
        return shardJedisManager.getRedisCount();
    }
    
    @Override
    public Boolean exists(String key) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.exists(key);
        }
    }
    
    @Override
    public Long expire(String key, int seconds) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.expire(key, seconds);
        }
    }
    
    @Override
    public Long del(String key) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.del(key);
        }
    }
    
    @Override
    public Long delOnAllRedis(String key) {
        for (int i = 0; i < shardJedisManager.getRedisCount(); i++) {
            try (Jedis jedis = shardJedisManager.getJedisByIndex(i)) {
                jedis.del(key);
            }
        }
        return 1L;
    }
    
    @Override
    public String set(String key, String value) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.set(key, value);
        }
    }
    
    @Override
    public String get(String key) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.get(key);
        }
    }
    
    @Override
    public Long incr(String key) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(key.hashCode())) {
            return jedis.incr(key);
        }
    }
    
    @Override
    public void hsetOnAllRedis(String key, List<Map<String, String>> hashList) {
        for (int i = 0; i < hashList.size(); i++) {
            try(Jedis jedis = shardJedisManager.getJedisByIndex(i)) {
                jedis.hset(key, hashList.get(i));
            }
        }
    }
    
    @Override
    public List<Map<String, String>> hgetAllOnAllRedis(String key) {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < shardJedisManager.getRedisCount(); i++) {
            try (Jedis jedis = shardJedisManager.getJedisByIndex(i)) {
                list.add(jedis.hgetAll(key));
            }
        }
        return list;    }
    
    @Override
    public Object eval(Long hashKey, String script) {
        try (Jedis jedis = shardJedisManager.getJedisByHashKey(hashKey)){
            return jedis.eval(script);
        }
    }
}
