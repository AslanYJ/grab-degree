package com.grab.degree.topic.course.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grab.degree.common.redis.RedisCache;
import com.grab.degree.topic.course.TopicCourseServiceApplication;

/**
 * 测试redis客户端
 * @author yjlan
 */
@SpringBootTest(classes = TopicCourseServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisClient {
    
    @Resource
    private RedisCache redisCache;
    
    @Test
    public void test() {
        redisCache.set(1+"",1+"",6000);
    }
}
