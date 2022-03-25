package com.grab.degree.topic.course.domain.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 新增一个活动
 * @author yjlan
 */
@Builder
@Getter
@ToString
public class AddNewActivityDTO implements Serializable {
    
    private final Long courseId;
    
    private final Integer degreeNum;
    
    /**
     * 活动开始时间，时间戳的形式
     */
    private final Long startTime;
    
    /**
     * 活动结束时间
     */
    private final Long endTime;
    
    
}
