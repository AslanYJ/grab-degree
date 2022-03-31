package com.grab.degree.activity.api;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.activity.domain.dto.TestDTO;

/**
 * test api
 * @author yjlan
 */
public interface TestApi {
    
    /**
     * 测试dubbo接口
     * @param testDTO 参数
     * @return 返回结果
     */
    ResponseResult<String> test(TestDTO testDTO);
}
