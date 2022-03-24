package com.grab.degree.topic.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author yjlan
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TopicCourseServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TopicCourseServiceApplication.class, args);
    }
}
