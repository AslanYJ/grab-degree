package com.yjlan.user.credit.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户学时返回类
 * @author yjlan
 */
@Data
public class UserCreditHourInfoVO implements Serializable {
    
    private Long userId;
    
    /**
     * 用户总学时
     */
    private Integer totalCreditHour;
    
    /**
     * 可用的用户学时
     */
    private Integer availableCreditHour;
    
    /**
     * 已用的学时
     */
    private Integer usedCreditHour;

    /**
     * 冻结学时
     */
    private Integer freezeCreditHour;
}
