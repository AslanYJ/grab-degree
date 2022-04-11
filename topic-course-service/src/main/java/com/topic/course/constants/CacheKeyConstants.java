package com.topic.course.constants;

/**
 * key
 * @author yjlan
 */
public class CacheKeyConstants {
    
    public static final String CHECK_REPEAT_CONSUME_ACTIVITY_KEY = "check_repeat_consume_activity_key:";
    
    public static String buildCheckRepeatKey(Long userId,Long activityId) {
        return CHECK_REPEAT_CONSUME_ACTIVITY_KEY + activityId + ":" + userId;
    }
}
