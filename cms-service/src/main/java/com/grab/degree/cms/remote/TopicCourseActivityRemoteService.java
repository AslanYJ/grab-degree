package com.grab.degree.cms.remote;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import com.grab.degree.cms.dao.CourseInfoDAO;
import com.grab.degree.cms.domain.dto.AddTopicCourseActivityDTO;
import com.grab.degree.cms.domain.entity.CourseInfo;
import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.activity.api.TopicCourseActivityApi;
import com.grab.degree.activity.domain.dto.AddNewActivityDTO;

/**
 * dubbo服务
 * @author yjlan
 */
@Component
public class TopicCourseActivityRemoteService {
    
    @DubboReference(version = "1.0.0",retries = 0)
    private TopicCourseActivityApi topicCourseActivityApi;
    
    @Resource
    private CourseInfoDAO courseInfoDAO;
    
    public ResponseResult<Void> addNewActivity(AddTopicCourseActivityDTO addTopicCourseActivityDTO) {
        CourseInfo courseInfo = courseInfoDAO.getById(addTopicCourseActivityDTO.getCourseId());
        AddNewActivityDTO addNewActivityDTO = AddNewActivityDTO.builder()
                .courseId(addTopicCourseActivityDTO.getCourseId())
                .degreeNum(addTopicCourseActivityDTO.getDegreeNum())
                .startTime(addTopicCourseActivityDTO.getStartTime())
                .endTime(addTopicCourseActivityDTO.getEndTime())
                .courseName(courseInfo.getCourseName())
                .courseHour(courseInfo.getCourseHour())
                .minAge(courseInfo.getMinAge())
                .maxAge(courseInfo.getMaxAge())
                .build();
        return topicCourseActivityApi.addNewActivity(addNewActivityDTO);
    }
}
