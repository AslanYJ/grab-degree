package com.grab.degree.topic.course.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.grab.degree.common.redis.RedisCache;
import com.grab.degree.topic.course.TopicCourseServiceApplication;
import com.grab.degree.topic.course.config.ShardJedisClient;
import com.grab.degree.topic.course.constants.CacheKeyConstants;

/**
 * 测试redis客户端
 * @author yjlan
 */
@SpringBootTest(classes = TopicCourseServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisClient {
    
    @Resource
    private RedisCache redisCache;
    
    @Resource
    private ShardJedisClient shardJedisClient;
    
    @Test
    public void test() {
        int degreeNum = 10;
        int redisCount = shardJedisClient.getRedisCount();
        // key:redis上的索引，value是对应的数量，其实就是均匀分配到每台实例上
        Map<Integer,Integer> map = new HashMap<>(16);
        for(int i = 0; i < degreeNum; i++) {
            int index = i % redisCount;
            map.putIfAbsent(index, 0);
            map.put(index, map.get(index) + 1);
        }
    
        List<Map<String,String>> hashList = new ArrayList<>();
        for(int i = 0; i < redisCount;i++) {
            Map<String,String> degreeNumMap = new HashMap<>(3);
            degreeNumMap.put(CacheKeyConstants.TOTAL_DEGREE_NUM,map.get(i) + "");
            degreeNumMap.put(CacheKeyConstants.HAS_GRAB_DEGREE_NUM,"0");
            degreeNumMap.put(CacheKeyConstants.REMAINING_GRAB_DEGREE_NUM,"0");
            hashList.add(degreeNumMap);
        }
        shardJedisClient.hsetOnAllRedis(CacheKeyConstants.DEGREE_NUM + 1,hashList);    }
    
    @Test
    public void testSet() {
        shardJedisClient.set("hello","hello");
    }
}
