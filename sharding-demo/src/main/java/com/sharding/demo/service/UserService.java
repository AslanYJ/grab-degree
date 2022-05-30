package com.sharding.demo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sharding.demo.domain.entity.User;
import com.sharding.demo.mapper.UserMapper;

/**
 * @author yjlan
 * @version V1.0
 * @Description service
 * @date 2022.05.30 09:06
 */
@Service
public class UserService {
    
    @Resource
    private UserMapper userMapper;
    
    
    public void save(Long userId,String value) {
        User user = new User();
        user.setUserId(userId);
        user.setValue(value);
        userMapper.insertV2(user);
    }
    
    public String detail(Long userId) {
        return JSON.toJSONString(userMapper.selectByUserId(userId));
    }
}
