package com.grab.degree.topic.course.service;

import java.util.List;

import com.grab.degree.topic.course.domain.vo.TopicActivityInfoVO;

/**
 * service
 * @author yjlan
 */
public interface TopicCourseActivityService {
    
    /**
     * 获取所有正在执行的活动
     * @return 返回所有活动
     */
    List<TopicActivityInfoVO> listAllActivity();
    
    /**
     * 初始化学位数量
     */
    void initDegreeNum();
}
