package com.grab.degree.cms.service;

import com.grab.degree.cms.domain.dto.AddTopicCourseActivityDTO;

/**
 * CMS活动管理服务
 * @author yjlan
 */
public interface TopicCourseActivityService {
    
    /**
     * 新增活动信息
     * @param addTopicCourseActivityDTO 参数
     */
    void addTopicCourseActivity(AddTopicCourseActivityDTO addTopicCourseActivityDTO);
}
