package com.grab.degree.topic.course.schedule;

import org.springframework.stereotype.Component;

import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时进行缓存预热初始化学位的数据
 * @author yjlan
 */
@Component
@Slf4j
public class AutoLoadCacheTask {
    
    
    @XxlJob("AutoLoadCacheTask")
    public void execute() {
        log.info("xxl-job..............");
    }
}
