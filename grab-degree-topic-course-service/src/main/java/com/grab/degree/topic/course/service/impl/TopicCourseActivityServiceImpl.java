package com.grab.degree.topic.course.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.grab.degree.common.redis.RedisCache;
import com.grab.degree.topic.course.constants.CacheKeyConstants;
import com.grab.degree.topic.course.dao.TopicCourseActivityDAO;
import com.grab.degree.topic.course.domain.entity.TopicCourseActivity;
import com.grab.degree.topic.course.domain.vo.TopicActivityInfoVO;
import com.grab.degree.topic.course.enums.TopicCourseActivityStatusEnum;
import com.grab.degree.topic.course.service.TopicCourseActivityService;

import lombok.val;

/**
 * 实现类
 *
 * @author yjlan
 */
@Service
public class TopicCourseActivityServiceImpl implements TopicCourseActivityService, InitializingBean {
    
    @Resource
    private TopicCourseActivityDAO topicCourseActivityDAO;
    
    @Resource
    private RedisCache redisCache;
    
    /**
     * guava缓存课程数据
     */
    private final LoadingCache<String,List<TopicActivityInfoVO>>
            currentMinuteActivityCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            // 每30s刷新一次缓存
            .refreshAfterWrite(30, TimeUnit.SECONDS)
            .build(new CacheLoader<String, List<TopicActivityInfoVO>>() {
                @SuppressWarnings("NullableProblems")
                @Override
                public List<TopicActivityInfoVO> load(String key) {
                    return loadActivityList();
                }
            });
    
    
    
    @Override
    public List<TopicActivityInfoVO> listAllActivity() {
        String currentMinute = FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(new Date());
        // 获得一分钟以内的缓存数据，如果不存在就去重新从redis中获取
        // 学位的实际数量是保存在redis的，课程信息和学位数量都从redis中获取，
        // 如果redis中没有，那么就是还没有进行预热，这个时候就得先从Mysql中获取学位的数量进行填充展示
        try {
             return currentMinuteActivityCache.get(currentMinute);
        } catch (ExecutionException e) {
            List<TopicActivityInfoVO> list = loadActivityList();
            currentMinuteActivityCache.put(currentMinute,list);
            return list;
        }
    }
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        String currentMinute = FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(new Date());
        currentMinuteActivityCache.put(currentMinute,loadActivityList());
    }
    
    private List<TopicActivityInfoVO> loadActivityList() {
        List<TopicCourseActivity> topicCourseActivities = topicCourseActivityDAO.listAllActivity();
        if (CollectionUtils.isEmpty(topicCourseActivities)) {
            return Collections.emptyList();
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        List<TopicActivityInfoVO> activityInfoVOList = new ArrayList<>(topicCourseActivities.size());
        for (TopicCourseActivity topicCourseActivity : topicCourseActivities) {
            TopicActivityInfoVO vo = new TopicActivityInfoVO();
            // 获取课程信息
            Map<String, String> courseInfoMap = redisCache
                    .hGetAll(CacheKeyConstants.TOPIC_COURSE_ACTIVITY_COURSE_INFO + topicCourseActivity.getId());
            vo.setStartTime(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
            vo.setEndTime(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
            vo.setCourseId(Long.valueOf(courseInfoMap.getOrDefault("courseId","-1")));
            vo.setCourseName(courseInfoMap.getOrDefault("courseName","-1"));
            vo.setCourseHour(Double.valueOf(courseInfoMap.getOrDefault("courseHour","-1")));
            vo.setMinAge(Integer.valueOf(courseInfoMap.getOrDefault("minAge","-1")));
            vo.setMaxAge(Integer.valueOf(courseInfoMap.getOrDefault("maxAge","-1")));
            vo.setStatus(topicCourseActivity.getStatus());
            // 进行中的库存从Redis中读取
            if (topicCourseActivity.getStatus().equals(TopicCourseActivityStatusEnum.ACTIVITY_PROCESSING.getStatus())) {
                // todo
            } else {
                vo.setDegreeNum(topicCourseActivity.getDegreeNum());
            }
            activityInfoVOList.add(vo);
        }
        return activityInfoVOList;
    }
}
