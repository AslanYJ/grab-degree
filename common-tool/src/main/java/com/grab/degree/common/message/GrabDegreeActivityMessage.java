package com.grab.degree.common.message;

import java.io.Serializable;

import lombok.Data;

/**
 * 抢购消息
 * @author yjlan
 */
@Data
public class GrabDegreeActivityMessage implements Serializable {
    
    private Long userId;
    
    private Long courseId;
    
    private Long activityId;
}
