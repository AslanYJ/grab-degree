package com.grab.degree.cms.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grab.degree.cms.domain.dto.AddTopicCourseActivityDTO;
import com.grab.degree.cms.remote.TopicCourseActivityRemoteService;
import com.grab.degree.common.resp.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 活动管理controller
 * @author yjlan
 */
@RestController
@RequestMapping("/topic/course/activity")
@Slf4j
public class TopicCourseActivityController {
    
    @Resource
    private TopicCourseActivityRemoteService topicCourseActivityRemoteService;
    
    @PostMapping("/addActivity")
    public ResponseResult<Void> addActivity(@RequestBody AddTopicCourseActivityDTO addTopicCourseActivityDTO) {
        return topicCourseActivityRemoteService.addNewActivity(addTopicCourseActivityDTO);
    }
}
