package com.grab.degree.activity.api.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import com.grab.degree.activity.api.TopicCourseActivityApi;
import com.grab.degree.activity.domain.dto.AddNewActivityDTO;
import com.grab.degree.common.redis.RedisCache;
import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.activity.constants.CacheKeyConstants;
import com.grab.degree.activity.dao.TopicCourseActivityDAO;
import com.grab.degree.activity.domain.entity.TopicCourseActivity;

/**
 * 专题课活动管理实现类
 *
 * @author yjlan
 */
@DubboService(version = "1.0.0", interfaceClass = TopicCourseActivityApi.class)
public class TopicCourseActivityApiImpl implements TopicCourseActivityApi {
    
    @Resource
    private TopicCourseActivityDAO topicCourseActivityDAO;
    
    @Resource
    private RedisCache redisCache;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult<Void> addNewActivity(AddNewActivityDTO addNewActivityDTO) {
        TopicCourseActivity topicCourseActivity = new TopicCourseActivity();
        topicCourseActivity.setCourseId(addNewActivityDTO.getCourseId())
                .setDegreeNum(addNewActivityDTO.getDegreeNum())
                .setStartTime(LocalDateTime.ofEpochSecond(addNewActivityDTO.getStartTime(), 0, ZoneOffset.ofHours(8)))
                .setEndTime(LocalDateTime.ofEpochSecond(addNewActivityDTO.getEndTime(),0,ZoneOffset.ofHours(8)));
        boolean saveResult = topicCourseActivityDAO.save(topicCourseActivity);
        // 保存课程数据到redis库中
        if (!saveResult) {
            return ResponseResult.fail();
        }
        saveCourseInfoToRedis(topicCourseActivity.getId(),addNewActivityDTO);
        return ResponseResult.success();
    }
    
    private void saveCourseInfoToRedis(Long id, AddNewActivityDTO addNewActivityDTO) {
        String key = CacheKeyConstants.TOPIC_COURSE_ACTIVITY_COURSE_INFO + id;
        Map<String,String> courseInfoMap = new HashMap<>(5);
        courseInfoMap.put("courseId",String.valueOf(addNewActivityDTO.getCourseId()));
        courseInfoMap.put("courseName",addNewActivityDTO.getCourseName());
        courseInfoMap.put("courseHour",String.valueOf(addNewActivityDTO.getCourseHour()));
        courseInfoMap.put("minAge",String.valueOf(addNewActivityDTO.getMinAge()));
        courseInfoMap.put("maxAge",String.valueOf(addNewActivityDTO.getMaxAge()));
        redisCache.hPutAll(key,courseInfoMap);
    }
}
