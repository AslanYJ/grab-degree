package com.grab.degree.cms.remote;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import com.grab.degree.cms.domain.dto.AddTopicCourseActivityDTO;
import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.api.TopicCourseActivityApi;
import com.grab.degree.topic.course.domain.dto.AddNewActivityDTO;

/**
 * dubbo服务
 * @author yjlan
 */
@Component
public class TopicCourseActivityRemoteService {
    
    @DubboReference(version = "1.0.0",retries = 0)
    private TopicCourseActivityApi topicCourseActivityApi;
    
    public ResponseResult<Void> addNewActivity(AddTopicCourseActivityDTO addTopicCourseActivityDTO) {
        AddNewActivityDTO addNewActivityDTO = AddNewActivityDTO.builder()
                .courseId(addTopicCourseActivityDTO.getCourseId())
                .degreeNum(addTopicCourseActivityDTO.getDegreeNum())
                .startTime(addTopicCourseActivityDTO.getStartTime())
                .endTime(addTopicCourseActivityDTO.getEndTime())
                .build();
        return topicCourseActivityApi.addNewActivity(addNewActivityDTO);
    }
}
