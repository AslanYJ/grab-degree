package com.grab.degree.topic.course.service.impl;

import org.springframework.stereotype.Service;

import com.grab.degree.topic.course.service.GrabDegreeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 抢购服务
 * @author yjlan
 */
@Slf4j
@Service
public class GrabDegreeServiceImpl implements GrabDegreeService {
    
    
    @Override
    public boolean garbDegree(Long userId, Long activityId) {
        return false;
    }
}
