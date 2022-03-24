package com.grab.degree.cms.controller;

import javax.annotation.Resource;

import com.grab.degree.cms.remote.TestRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grab.degree.cms.service.TestBizService;
import com.grab.degree.common.resp.ResponseResult;

/**
 * TestController
 * @author yjlan
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    @Resource
    private TestBizService testBizService;

    @Resource
    private TestRemoteService testRemoteService;
    
    @GetMapping("/add")
    public ResponseResult<String> add() {
        testBizService.add();
        return ResponseResult.success("操作成功！");
    }

    @GetMapping("/testDubbo")
    public ResponseResult<String> testDubbo() {
        return ResponseResult.success(testRemoteService.test());
    }
}
