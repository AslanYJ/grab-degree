package com.grab.degree.topic.course.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.grab.degree.common.redis.RedisCache;
import com.grab.degree.topic.course.config.ShardJedisClient;
import com.grab.degree.topic.course.constants.CacheKeyConstants;
import com.grab.degree.topic.course.dao.TopicCourseActivityDAO;
import com.grab.degree.topic.course.domain.entity.TopicCourseActivity;
import com.grab.degree.topic.course.domain.vo.TopicActivityInfoVO;
import com.grab.degree.topic.course.enums.TopicCourseActivityStatusEnum;
import com.grab.degree.topic.course.service.TopicCourseActivityService;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * 实现类
 *
 * @author yjlan
 */
@Service
@Slf4j
public class TopicCourseActivityServiceImpl implements TopicCourseActivityService, InitializingBean {
    
    /**
     * 时间间隔
     */
    private static final int TIME_MINUTE = 5;
    
    @Resource
    private TopicCourseActivityDAO topicCourseActivityDAO;
    
    @Resource
    private RedisCache redisCache;
    
    @Resource
    private ShardJedisClient shardJedisClient;
    
    /**
     * guava缓存课程数据
     */
    private final LoadingCache<String, List<TopicActivityInfoVO>>
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
            currentMinuteActivityCache.put(currentMinute, list);
            return list;
        }
    }
    
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initDegreeNum() {
        // 查询所所有待开始的活动,学位数据初始化后，就是进行中
        List<TopicCourseActivity> topicCourseActivities = topicCourseActivityDAO.listAllWaitStartActivity();
        if (CollectionUtils.isEmpty(topicCourseActivities)) {
            return;
        }
        for (TopicCourseActivity topicCourseActivity : topicCourseActivities) {
            LocalDateTime startTime = topicCourseActivity.getStartTime();
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            long minutes = duration.toMinutes();
            // 如果是相差5分钟到，证明是开始时间的5分钟前了
            if (minutes <= TIME_MINUTE) {
                initToRedis(topicCourseActivity);
                //修改状态,可以优化成批量
                topicCourseActivity.setStatus(TopicCourseActivityStatusEnum.ACTIVITY_PROCESSING.getStatus());
                topicCourseActivityDAO.updateById(topicCourseActivity);
            }
        }
    }
    
    private void initToRedis(TopicCourseActivity topicCourseActivity) {
        int degreeNum = topicCourseActivity.getDegreeNum();
        int redisCount = shardJedisClient.getRedisCount();
        // key:redis上的索引，value是对应的数量，其实就是均匀分配到每台实例上
        Map<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < degreeNum; i++) {
            int index = i % redisCount;
            map.putIfAbsent(index, 0);
            map.put(index, map.get(index) + 1);
        }
        
        List<Map<String, String>> hashList = new ArrayList<>();
        for (int i = 0; i < redisCount; i++) {
            Map<String, String> degreeNumMap = new HashMap<>(3);
            degreeNumMap.put(CacheKeyConstants.TOTAL_DEGREE_NUM, map.get(i) + "");
            degreeNumMap.put(CacheKeyConstants.HAS_GRAB_DEGREE_NUM, "0");
            degreeNumMap.put(CacheKeyConstants.REMAINING_GRAB_DEGREE_NUM, "0");
            hashList.add(degreeNumMap);
            log.info("学位分片 degreeNumMap={}", JSON.toJSONString(degreeNumMap));
        }
        shardJedisClient.hsetOnAllRedis(CacheKeyConstants.DEGREE_NUM + topicCourseActivity.getId(), hashList);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        String currentMinute = FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(new Date());
        currentMinuteActivityCache.put(currentMinute, loadActivityList());
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
            vo.setCourseId(Long.valueOf(courseInfoMap.getOrDefault("courseId", "-1")));
            vo.setCourseName(courseInfoMap.getOrDefault("courseName", "-1"));
            vo.setCourseHour(Double.valueOf(courseInfoMap.getOrDefault("courseHour", "-1")));
            vo.setMinAge(Integer.valueOf(courseInfoMap.getOrDefault("minAge", "-1")));
            vo.setMaxAge(Integer.valueOf(courseInfoMap.getOrDefault("maxAge", "-1")));
            vo.setStatus(topicCourseActivity.getStatus());
            // 进行中的库存从Redis中读取
            vo.setDegreeNum(topicCourseActivity.getDegreeNum());
            if (topicCourseActivity.getStatus().equals(TopicCourseActivityStatusEnum.ACTIVITY_PROCESSING.getStatus())) {
                List<Map<String, String>> mapList = shardJedisClient
                        .hgetAllOnAllRedis(CacheKeyConstants.DEGREE_NUM + topicCourseActivity.getId());
                for (Map<String, String> stringStringMap : mapList) {
                    vo.setHasGrabDegreeNum(vo.getHasGrabDegreeNum() +
                            Integer.parseInt(stringStringMap.get(CacheKeyConstants.HAS_GRAB_DEGREE_NUM)));
                    vo.setRemainingGrabDegreeNum(vo.getRemainingGrabDegreeNum() +
                            Integer.parseInt(stringStringMap.get(CacheKeyConstants.REMAINING_GRAB_DEGREE_NUM)));
                }
            }
            activityInfoVOList.add(vo);
        }
        return activityInfoVOList;
    }
}
