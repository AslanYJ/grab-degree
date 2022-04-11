package com.grab.degree.activity.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.grab.degree.activity.config.mq.producer.DefaultProducer;
import com.grab.degree.activity.domain.dto.GrabDegreeDTO;
import com.grab.degree.activity.service.GrabDegreeService;
import com.grab.degree.common.constants.RocketMqConstants;
import com.grab.degree.common.message.GrabDegreeActivityMessage;
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
    
    @Resource
    private DefaultProducer defaultProducer;
    
    @PostMapping("/grab")
    public ResponseResult<Boolean> grab(@RequestBody GrabDegreeDTO grabDegreeDTO) {
        boolean result = grabDegreeService.garbDegree(grabDegreeDTO);
        if (result) {
            return ResponseResult.success("报名成功，请耐心等待处理结果~");
        }
        
        return ResponseResult.fail(ResponseCode.FAIL.getCode(),"报名失败，请检查是否符合报名条件");
    }
    
    
    @GetMapping("/testMq")
    public ResponseResult<Void> testMq() {
        GrabDegreeActivityMessage message = new GrabDegreeActivityMessage();
        message.setUserId(6666L);
        defaultProducer.sendMessage(RocketMqConstants.GRAB_DEGREE_ACTIVITY_PRODUCER_TOPIC,
                JSON.toJSONString(message),"抢购服务抢购成功",null,
                String.valueOf(message.getUserId()));
        return ResponseResult.success();
    }
}
