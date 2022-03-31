package com.grab.degree.activity.constants;

/**
 * 缓存key的
 * @author yjlan
 */
public class CacheKeyConstants {
    
    /**
     * 保存课程的key
     */
    public static final String TOPIC_COURSE_ACTIVITY_COURSE_INFO = "topic_course_activity_course_info:";
    
    /**
     * 学位数量的key
     */
    public static final String DEGREE_NUM = "degree_num:";
    
    /**
     * 学位数量的hash field
     */
    public static final String TOTAL_DEGREE_NUM = "totalGrabDegreeNum";
    
    /**
     * 已经抢购数量
     */
    public static final String HAS_GRAB_DEGREE_NUM = "hasGrabDegreeNum";
    
    
    public static final String SCRIPT = "local degree_num_key = '%s';"
            + "local totalGrabDegreeNum = redis.call('hget',degree_num_key,'totalGrabDegreeNum') + 0;"
            + "local hasGrabDegreeNum = redis.call('hget',degree_num_key,'hasGrabDegreeNum') + 0;"
            + "if(totalGrabDegreeNum > 0)"
            + "then "
            + "redis.call('hset',degree_num_key,'totalGrabDegreeNum',totalGrabDegreeNum - 1);"
            + "redis.call('hset',degree_num_key,'hasGrabDegreeNum',hasGrabDegreeNum +  1);"
            + "return 'success';"
            + "else "
            + "return 'fail';"
            + "end;";
}
