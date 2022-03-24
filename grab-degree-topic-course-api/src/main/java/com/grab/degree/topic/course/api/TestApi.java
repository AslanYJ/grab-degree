package com.grab.degree.topic.course.api;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.domain.dto.TestDTO;

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
