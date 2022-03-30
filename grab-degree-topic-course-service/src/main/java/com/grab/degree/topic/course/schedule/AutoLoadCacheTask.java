package com.grab.degree.topic.course.schedule;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.grab.degree.topic.course.service.TopicCourseActivityService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时进行缓存预热初始化学位的数据
 * @author yjlan
 */
@Component
@Slf4j
public class AutoLoadCacheTask {
    
    @Resource
    private TopicCourseActivityService topicCourseActivityService;
    
    @XxlJob("AutoLoadCacheTask")
    public void execute() {
        log.info("初始化学位分片数据.....");
        topicCourseActivityService.initDegreeNum();
        log.info("初始化工作结束........");
    }
}
