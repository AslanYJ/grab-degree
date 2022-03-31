package com.yjlan.user.center.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 * @author yjlan
 */
@Data
public class UserInfoVO implements Serializable {
    
    private Long userId;
    
    private String fullName;
    
    private String nickName;
    
    private Integer age;
    
    
}
