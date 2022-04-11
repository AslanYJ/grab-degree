package com.topic.course.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topic.course.dao.TopicCourseActivityUserDegreeDAO;
import com.topic.course.domain.entity.TopicCourseActivityUserDegree;
import com.topic.course.service.TopicCourseActivityUserDegreeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 实现类
 * @author yjlan
 */
@Service
@Slf4j
public class TopicCourseActivityUserDegreeServiceImpl implements TopicCourseActivityUserDegreeService {
    
    @Resource
    private TopicCourseActivityUserDegreeDAO topicCourseActivityUserDegreeDAO;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserDegree(Long userId, Long activityId, Long courseId) {
        TopicCourseActivityUserDegree check = topicCourseActivityUserDegreeDAO.getByActivityIdAndUserId(activityId,userId);
        if (check != null) {
            return;
        }
        TopicCourseActivityUserDegree topicCourseActivityUserDegree = new TopicCourseActivityUserDegree();
        topicCourseActivityUserDegree.setUserId(userId);
        topicCourseActivityUserDegree.setActivityId(activityId);
        topicCourseActivityUserDegree.setCourseId(courseId);
        topicCourseActivityUserDegreeDAO.save(topicCourseActivityUserDegree);
    }
}
