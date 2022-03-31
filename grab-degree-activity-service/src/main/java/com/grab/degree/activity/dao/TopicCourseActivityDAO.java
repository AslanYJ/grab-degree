package com.grab.degree.activity.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grab.degree.activity.domain.entity.TopicCourseActivity;
import com.grab.degree.activity.enums.TopicCourseActivityStatusEnum;
import com.grab.degree.activity.mapper.TopicCourseActivityMapper;
import com.grab.degree.common.dao.BaseDAO;

/**
 * Dao
 *
 * @author yjlan
 */
@Repository
public class TopicCourseActivityDAO extends BaseDAO<TopicCourseActivityMapper, TopicCourseActivity> {
    
    /**
     * 查询所有活动
     *
     * @return 活动
     */
    public List<TopicCourseActivity> listAllActivity() {
        LambdaQueryWrapper<TopicCourseActivity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Integer> status = new ArrayList<>(2);
        // 需要查出未开始,或者是正在进行中的活动
        status.add(TopicCourseActivityStatusEnum.ACTIVITY_PROCESSING.getStatus());
        status.add(TopicCourseActivityStatusEnum.NEW_CREATE.getStatus());
        lambdaQueryWrapper.in(TopicCourseActivity::getStatus, status);
        return baseMapper.selectList(lambdaQueryWrapper);
    }
    
    
    public List<TopicCourseActivity> listAllWaitStartActivity() {
        LambdaQueryWrapper<TopicCourseActivity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TopicCourseActivity::getStatus, TopicCourseActivityStatusEnum.NEW_CREATE.getStatus());
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
