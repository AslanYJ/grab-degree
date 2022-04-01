package com.grab.degree.activity.domain.dto;

import lombok.Data;

/**
 * 抢学位的dto
 * @author yjlan
 */
@Data
public class GrabDegreeDTO {


    private Long userId;

    private Long activityId;

    private Double courseHour;

    private Integer minAge;

    private Integer maxAge;

}
