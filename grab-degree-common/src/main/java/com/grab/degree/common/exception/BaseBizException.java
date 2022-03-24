package com.grab.degree.common.exception;

import java.text.MessageFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础异常类
 * @author yjlan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBizException extends RuntimeException{
    /**
     * 默认错误码
     */
    private static final String DEFAULT_ERROR_CODE = "-1";
    
    private String errorCode;
    
    private String errorMsg;
    
    public BaseBizException(String errorMsg) {
        super(errorMsg);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = errorMsg;
    }
    
    public BaseBizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public BaseBizException(BaseErrorCodeEnum baseErrorCodeEnum) {
        super(baseErrorCodeEnum.getErrorMsg());
        this.errorCode = baseErrorCodeEnum.getErrorCode();
        this.errorMsg = baseErrorCodeEnum.getErrorMsg();
    }
    
    public BaseBizException(String errorCode, String errorMsg, Object... arguments) {
        super(MessageFormat.format(errorMsg, arguments));
        this.errorCode = errorCode;
        this.errorMsg = MessageFormat.format(errorMsg, arguments);
    }
    
    public BaseBizException(BaseErrorCodeEnum baseErrorCodeEnum, Object... arguments) {
        super(MessageFormat.format(baseErrorCodeEnum.getErrorMsg(), arguments));
        this.errorCode = baseErrorCodeEnum.getErrorCode();
        this.errorMsg = MessageFormat.format(baseErrorCodeEnum.getErrorMsg(), arguments);
    }
}
