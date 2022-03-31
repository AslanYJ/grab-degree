package com.grab.degree.activity.api.impl;

import com.grab.degree.activity.api.TestApi;
import com.grab.degree.activity.domain.dto.TestDTO;
import com.grab.degree.common.resp.ResponseResult;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0", interfaceClass = TestApi.class, retries = 0)
@Slf4j
public class TestApiImpl implements TestApi {

    @Override
    public ResponseResult<String> test(TestDTO testDTO) {
        return ResponseResult.success(testDTO.getMessage());
    }
}
