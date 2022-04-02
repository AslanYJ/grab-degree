package com.grab.degree.activity.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grab.degree.activity.domain.dto.GrabDegreeDTO;
import com.grab.degree.activity.service.GrabDegreeService;
import com.grab.degree.common.resp.ResponseCode;
import com.grab.degree.common.resp.ResponseResult;

/**
 * 抢学位Controller
 * @author yjlan
 */
@RestController
@RequestMapping("/grabDegree/")
public class GrabDegreeController {
    
    @Resource
    private GrabDegreeService grabDegreeService;
    
    @PostMapping("/grab")
    public ResponseResult<Boolean> grab(@RequestBody GrabDegreeDTO grabDegreeDTO) {
        boolean result = grabDegreeService.garbDegree(grabDegreeDTO);
        if (result) {
            return ResponseResult.success("报名成功，请耐心等待处理结果~");
        }
        
        return ResponseResult.fail(ResponseCode.FAIL.getCode(),"报名失败，请检查是否符合报名条件");
    }
}
