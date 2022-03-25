package com.grab.degree.topic.course.api;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.domain.dto.AddNewActivityDTO;

/**
 * 专题课服务管理api
 * @author yjlan
 */
public interface TopicCourseActivityApi {
    
    /**
     * 新增活动
     * @param addNewActivityDTO 参数
     * @return 结果
     */
    ResponseResult<Void> addNewActivity(AddNewActivityDTO addNewActivityDTO);
}
