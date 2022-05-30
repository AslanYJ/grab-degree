package com.sharding.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sharding.demo.service.UserService;

/**
 * @author yjlan
 * @version V1.0
 * @Description (这里用一句话描述这个类的作用)
 * @date 2022.05.30 09:09
 */
@RestController
public class UserController {
    
    @Resource
    private UserService userService;
    
    
    @GetMapping("/save/{userId}/{value}")
    public void save(@PathVariable("userId") Long userId,@PathVariable("value") String value) {
        userService.save(userId,value);
    }
    
    @GetMapping("/detail/{userId}")
    public String detail(@PathVariable("userId") Long userId) {
        return userService.detail(userId);
    }
}
