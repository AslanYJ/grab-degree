package com.topic.course.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grab.degree.common.dao.BaseDAO;
import com.topic.course.domain.entity.TopicCourseActivityUserDegree;
import com.topic.course.mapper.TopicCourseActivityUserDegreeMapper;

/**
 * DAO
 *
 * @author yjlan
 */
@Repository
public class TopicCourseActivityUserDegreeDAO extends
        BaseDAO<TopicCourseActivityUserDegreeMapper, TopicCourseActivityUserDegree> {
    
    
    public TopicCourseActivityUserDegree getByActivityIdAndUserId(Long activityId, Long userId) {
        return getOne(new LambdaQueryWrapper<TopicCourseActivityUserDegree>()
                .eq(TopicCourseActivityUserDegree::getActivityId, activityId)
                .eq(TopicCourseActivityUserDegree::getUserId,userId)
        );
    }
}
