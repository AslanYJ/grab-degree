package com.topic.course.service;

/**
 * @author yjlan
 */
public interface TopicCourseActivityUserDegreeService {
    
    /**
     * 新增学位
     * @param userId 用户id
     * @param activityId 活动id
     * @param courseId 课程id
     */
    void saveUserDegree(Long userId,Long activityId,Long courseId);
}
