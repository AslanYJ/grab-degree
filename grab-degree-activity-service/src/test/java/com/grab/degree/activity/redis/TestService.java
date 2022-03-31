package com.grab.degree.activity.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grab.degree.activity.ActivityServiceApplication;
import com.grab.degree.activity.service.TopicCourseActivityService;

/**
 * 测试服务，命名不规范，以贵司的要求为准
 * @author yjlan
 */
@SpringBootTest(classes = ActivityServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestService {

    @Resource
    private TopicCourseActivityService topicCourseActivityService;
    
    @Test
    public void testInit() {
        topicCourseActivityService.initDegreeNum();
    }
}
