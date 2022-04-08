package com.grab.degree.activity.config.redis;

import java.util.List;
import java.util.Map;

/**
 * 接口
 * @author yjlan
 */
public interface IShardJedisClient {
    
    /**
     * 获取redis的数量
     * @return 返回redis的数量
     */
    int getRedisCount();
    
    /**
     * 判断某个key是否存在
     * @param key key值
     * @return 返回key是否存在
     */
    Boolean exists(String key);
    
    /**
     * 过期某个key
     * @param key key值
     * @param seconds 时间，单位：秒
     * @return TTL
     */
    Long expire(String key, int seconds);
    
    /**
     * 删除键
     * @param key key值
     * @return 删除结果
     */
    Long del(String key);
    
    /**
     * 删除所有的对应的key
     * @param key key值
     * @return 返回结果
     */
    Long delOnAllRedis(String key);
    
    /**
     * 设置一个key值
     * @param key key
     * @param value value
     * @return 结果
     */
    String set(String key, String value);

    /**
     * 设置有过期时间的key
     * @param key key
     * @param value 值
     * @param seconds 秒
     * @return 返回值
     */
    String set(String key,String value,int seconds);
    
    /**
     * get一个key值
     * @param key key
     * @return value
     */
    String get(String key);
    
    /**
     * 自增
     * @param key key
     * @return 自增后的结果
     */
    Long incr(String key);
    
    /**
     * hash结构，将数据分布在redis集群中
     * @param key key
     * @param hashList 数据
     */
    void hsetOnAllRedis(String key, List<Map<String, String>> hashList);
    
    /**
     * hash结构，拿到集群中的所有分片数据
     * @param key key值
     * @return 数据
     */
    List<Map<String, String>> hgetAllOnAllRedis(String key);
    
    /**
     * 执行lua脚本
     * @param hashKey hash值
     * @param script lua脚本
     * @return 结果
     */
    Object eval(Long hashKey, String script);
}
