package com.grab.degree.cms.domain.dto;

import lombok.Data;

/**
 * 新增一个活动信息
 * @author yjlan
 */
@Data
public class AddTopicCourseActivityDTO {

    private Long courseId;
    
    private Integer degreeNum;
    
    /**
     * 活动开始时间，时间戳的形式
     */
    private Long startTime;
    
    /**
     * 活动结束时间
     */
    private Long endTime;
    
}
