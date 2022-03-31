package com.grab.degree.common.exception;

/**
 * 基础异常枚举,枚举的时候实现该类
 * @author yjlan
 */
public interface BaseErrorCodeEnum {
    
    /**
     * 返回的code
     * @return code
     */
    String getErrorCode();
    
    /**
     * 返回的错误信息
     * @return 返回错误信息
     */
    String getErrorMsg();
}
