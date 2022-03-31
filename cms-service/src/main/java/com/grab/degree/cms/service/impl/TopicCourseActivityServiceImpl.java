package com.grab.degree.cms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.grab.degree.cms.domain.dto.AddTopicCourseActivityDTO;
import com.grab.degree.cms.remote.TopicCourseActivityRemoteService;
import com.grab.degree.cms.service.TopicCourseActivityService;
import com.grab.degree.common.resp.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 实现类
 * @author yjlan
 */
@Service
@Slf4j
public class TopicCourseActivityServiceImpl implements TopicCourseActivityService {
    
    @Resource
    private TopicCourseActivityRemoteService topicCourseActivityRemoteService;
    
    @Override
    public void addTopicCourseActivity(AddTopicCourseActivityDTO addTopicCourseActivityDTO) {
        ResponseResult<Void> responseResult = topicCourseActivityRemoteService.addNewActivity(addTopicCourseActivityDTO);
        if (!responseResult.isSuccess()) {
            log.info("调用dubbo接口同步数据失败..");
        }
    }
}
