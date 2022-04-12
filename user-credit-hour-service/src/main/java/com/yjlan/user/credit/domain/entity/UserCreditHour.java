package com.yjlan.user.credit.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjlan
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCreditHour implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 总学时
     */
    private Double totalCreditHour;

    /**
     * 可用学时
     */
    private Double availableCreditHour;

    /**
     * 已用学时
     */
    private Double usedCreditHour;

    /**
     * 冻结学时
     */
    private Double freezeCreditHour;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;


}
