package com.grab.degree.cms.remote;

import com.grab.degree.common.resp.ResponseResult;
import com.grab.degree.topic.course.api.TestApi;
import com.grab.degree.topic.course.domain.dto.TestDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class TestRemoteService {

    @DubboReference(version = "1.0.0",retries = 0)
    private TestApi testApi;

    public String test() {
        TestDTO testDTO = new TestDTO();
        testDTO.setMessage("test");
        ResponseResult<String> test = testApi.test(testDTO);
        return test.getData();
    }
}
