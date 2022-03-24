package com.grab.degree.cms.controller;

import javax.annotation.Resource;

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
    
    @GetMapping("/add")
    public ResponseResult<String> add() {
        testBizService.add();
        return ResponseResult.success("操作成功！");
    }
}
