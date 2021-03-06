package com.grab.degree.activity.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;

/**
 *
 * @author yjlan
 */
@Data
public class TopicActivityInfoVO {
    
    private String courseName;
    
    private Long courseId;
    
    private Double courseHour;
    
    private Integer minAge;
    
    private Integer maxAge;
    
    private Long startTime;
    
    private Long endTime;
    
    /**
     * 如果缓存没预热，那么直接查数据库的缓存
     */
    private Integer degreeNum;
    
    private Integer hasGrabDegreeNum;
    
    private Integer remainingGrabDegreeNum;
    
    private Integer status;
    
    public TopicActivityInfoVO() {
        this.degreeNum = 0;
        this.hasGrabDegreeNum = 0;
        this.remainingGrabDegreeNum = 0;
    }
    
}
