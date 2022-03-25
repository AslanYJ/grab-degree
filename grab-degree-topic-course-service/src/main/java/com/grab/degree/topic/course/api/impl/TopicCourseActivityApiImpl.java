package com.grab.degree.topic.course.api.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.api.TopicCourseActivityApi;
import com.grab.degree.topic.course.dao.TopicCourseActivityDAO;
import com.grab.degree.topic.course.domain.dto.AddNewActivityDTO;
import com.grab.degree.topic.course.domain.entity.TopicCourseActivity;

/**
 * 专题课活动管理实现类
 *
 * @author yjlan
 */
@DubboService(version = "1.0.0", interfaceClass = TopicCourseActivityApi.class)
public class TopicCourseActivityApiImpl implements TopicCourseActivityApi {
    
    @Resource
    private TopicCourseActivityDAO topicCourseActivityDAO;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult<Void> addNewActivity(AddNewActivityDTO addNewActivityDTO) {
        TopicCourseActivity topicCourseActivity = new TopicCourseActivity();
        topicCourseActivity.setCourseId(addNewActivityDTO.getCourseId())
                .setDegreeNum(addNewActivityDTO.getDegreeNum())
                .setStartTime(LocalDateTime.ofEpochSecond(addNewActivityDTO.getStartTime(), 0, ZoneOffset.ofHours(8)))
                .setEndTime(LocalDateTime.ofEpochSecond(addNewActivityDTO.getEndTime(),0,ZoneOffset.ofHours(8)));
        boolean saveResult = topicCourseActivityDAO.save(topicCourseActivity);
        if (!saveResult) {
            return ResponseResult.fail();
        }
        return ResponseResult.success();
    }
}
