package com.grab.degree.topic.course.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ShardJedisManager implements DisposableBean {

    private final List<JedisPool> jedisPools = new ArrayList<>();

    public ShardJedisManager(ShardJedisConfig shardJedisConfig) {
        // jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(shardJedisConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(shardJedisConfig.getMaxIdle());
        jedisPoolConfig.setMinIdle(shardJedisConfig.getMinIdle());
        for (ShardJedisConfig.Config config : shardJedisConfig.getConfigs()) {
            String[] ipAndPort = config.getAddr().split(":");
            String ip = ipAndPort[0];
            int port = Integer.parseInt(ipAndPort[1]);
            // 对于每一个redis实例都建立一个jedisPool
            JedisPool jedisPool;
            if (StringUtils.isBlank(config.getPassword())) {
                jedisPool = new JedisPool(jedisPoolConfig, ip, port, shardJedisConfig.getTimeOut());
            } else {
                jedisPool = new JedisPool(jedisPoolConfig, ip, port, shardJedisConfig.getTimeOut(), config.getPassword());
            }
            log.info("创建JedisPool, jedisPool={}", jedisPool);
            jedisPools.add(jedisPool);
        }
    }


    public int getRedisCount() {
        return jedisPools.size();
    }

    public Jedis getJedisByIndex(int index) {
        return jedisPools.get(index).getResource();
    }

    public Jedis getJedisByHashKey(int hashKey) {
        hashKey = Math.abs(hashKey);
        int index = (hashKey % getRedisCount());
        return getJedisByIndex(index);
    }
    
    public Jedis getJedisByHashKey(long hashKey) {
        hashKey = Math.abs(hashKey);
        int index = (int)(hashKey % getRedisCount());
        return getJedisByIndex(index);
    }

    @Override
    public void destroy() throws Exception {
        for (JedisPool jedisPool : jedisPools) {
            log.info("关闭jedisPool, jedisPool={}", jedisPool);
            jedisPool.close();
        }
    }
}
