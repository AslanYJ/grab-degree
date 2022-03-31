package com.grab.degree.activity.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.grab.degree.activity.enums.TopicCourseActivityStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjlan
 * @since 2022-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TopicCourseActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对应的courseId
     */
    private Long courseId;

    /**
     * 学位数量
     */
    private Integer degreeNum;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
    
    /**
     * 活动的状态
     * {@link TopicCourseActivityStatusEnum}
     */
    private Integer status;


}
