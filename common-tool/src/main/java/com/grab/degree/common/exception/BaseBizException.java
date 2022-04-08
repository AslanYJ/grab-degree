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

    
    private Integer errorCode;
    
    private String errorMsg;
    
    public BaseBizException(String errorMsg) {
        super(errorMsg);
        this.errorCode = BizCodeEnum.DEFAULT_ERROR_CODE.getErrorCode();
        this.errorMsg = errorMsg;
    }
    
    public BaseBizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public BaseBizException(BaseErrorCodeEnum baseErrorCodeEnum) {
        super(baseErrorCodeEnum.getErrorMsg());
        this.errorCode = baseErrorCodeEnum.getErrorCode();
        this.errorMsg = baseErrorCodeEnum.getErrorMsg();
    }
    
    public BaseBizException(Integer errorCode, String errorMsg, Object... arguments) {
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
