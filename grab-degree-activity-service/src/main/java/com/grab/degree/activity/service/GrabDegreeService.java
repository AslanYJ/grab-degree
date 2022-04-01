package com.grab.degree.activity.service;

import com.grab.degree.activity.domain.dto.GrabDegreeDTO;

/**
 * 抢学位的具体逻辑
 * @author yjlan
 */
public interface GrabDegreeService {
    
    /**
     * 抢学位逻辑
     * @param grabDegreeDTO 参数
     * @return 是否成功
     */
    boolean garbDegree(GrabDegreeDTO grabDegreeDTO);
}
