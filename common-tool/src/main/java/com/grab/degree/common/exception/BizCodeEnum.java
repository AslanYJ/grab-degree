package com.grab.degree.common.exception;

/**
 * 业务的错误码常量
 * @author yjlan
 */
public enum BizCodeEnum implements BaseErrorCodeEnum{
    /**
     * 业务码集中管理
     */
    DEFAULT_ERROR_CODE(-1,"处理失败"),
    MQ_SEND_FAIL(1000,"MQ消息发送失败");
    
    private final Integer errorCode;
    
    private final String errorMsg;
    
    BizCodeEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    @Override
    public Integer getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
