package com.grab.degree.cms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.grab.degree.cms.dao.TestDAO;
import com.grab.degree.cms.domain.entity.Test;

/**
 * 测试类
 * @author yjlan
 */
@Service
public class TestBizService {
    
    @Resource
    private TestDAO testDAO;
    
    
    public void add() {
        Test test = new Test();
        test.setId(1);
        testDAO.save(test);
    }
}
