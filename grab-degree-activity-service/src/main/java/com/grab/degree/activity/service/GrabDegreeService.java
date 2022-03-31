package com.grab.degree.activity.service;

/**
 * 抢学位的具体逻辑
 * @author yjlan
 */
public interface GrabDegreeService {
    
    /**
     * 抢学位逻辑
     * @param userId 用户id
     * @param activityId 活动id
     * @return 是否成功
     */
    boolean garbDegree(Long userId,Long activityId);
}
