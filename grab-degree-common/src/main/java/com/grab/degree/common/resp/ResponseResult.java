package com.grab.degree.common.resp;

import java.io.Serializable;

import lombok.Data;

/**
 * 统一的返回实体
 * @author yjlan
 */
@SuppressWarnings("rawtypes")
@Data
public class ResponseResult<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int code;
    
    private String message;
    
    private T data;
    
    public ResponseResult() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getDesc();
    }
    
    public ResponseResult(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
    
    public static <T> ResponseResult<T> success(T t) {
        @SuppressWarnings("unchecked")
        ResponseResult<T> result = new ResponseResult();
        result.setData(t);
        result.setMessage(ResponseCode.SUCCESS.getDesc());
        result.setCode(ResponseCode.SUCCESS.getCode());
        return result;
    }
    
    
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        return new ResponseResult<>(code,message);
    }
    
    public static <T> ResponseResult<T> fail(Integer code, String message, T t) {
        @SuppressWarnings("unchecked")
        ResponseResult<T> result = new ResponseResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData(t);
        return result;
    }
}
