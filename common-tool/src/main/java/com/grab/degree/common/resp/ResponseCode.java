package com.grab.degree.common.resp;

import lombok.Getter;

/**
 * 返回码
 * @author yjlan
 */
@Getter
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(1000, "处理成功"),
    /**
     * 失败
     */
    FAIL(2000, "处理失败"),
    CHECK_FAIL(3000,"参数校验失败");
    
    private final int code;
    private final String desc;
    
    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
