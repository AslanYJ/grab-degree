package com.grab.degree.topic.course.service.impl;

import java.util.concurrent.atomic.AtomicLong;

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
    
    /**
     * 顺序，用来决定在哪台机器上进行扣减
     */
    private static final AtomicLong SEQUENCER = new AtomicLong();
    
    @Override
    public boolean garbDegree(Long userId, Long activityId) {
        return false;
    }
}
