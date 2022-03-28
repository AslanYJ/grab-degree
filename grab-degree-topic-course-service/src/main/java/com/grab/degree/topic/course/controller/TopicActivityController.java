package com.grab.degree.topic.course.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.domain.vo.TopicActivityInfoVO;
import com.grab.degree.topic.course.service.TopicCourseActivityService;

/**
 * 活动controller，主要入口提供抢购、查询等服务
 *
 * @author yjlan
 */
@RestController
@RequestMapping("/topic/course/activity")
public class TopicActivityController {
    
    @Resource
    private TopicCourseActivityService topicCourseActivityService;
    
    @GetMapping("/listAllActivity")
    public ResponseResult<List<TopicActivityInfoVO>> listAllActivity() {
        return ResponseResult.success(topicCourseActivityService.listAllActivity());
    }
}
