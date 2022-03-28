package com.grab.degree.topic.course.enums;

import lombok.Getter;

/**
 * 活动的枚举
 * @author yjlan
 */
@Getter
public enum TopicCourseActivityStatusEnum {
    /**
     * 枚举
     */
    NEW_CREATE(0, "新创建"),
    ACTIVITY_PROCESSING(1,"活动进行中（数据已经预热）"),
    ACTIVITY_OVER(2,"活动已经结束"),
    ACTIVITY_DATA_CLEANED(3,"数据已经清理");
    
    
    private final int status;
    
    private final String statusDec;
    
    TopicCourseActivityStatusEnum(int status, String statusDec) {
        this.status = status;
        this.statusDec = statusDec;
    }
}
