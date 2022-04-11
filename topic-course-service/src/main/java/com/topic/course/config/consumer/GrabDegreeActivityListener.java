package com.topic.course.config.consumer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.grab.degree.common.message.GrabDegreeActivityMessage;
import com.grab.degree.common.redis.RedisCache;
import com.topic.course.constants.CacheKeyConstants;
import com.topic.course.service.TopicCourseActivityUserDegreeService;

import lombok.extern.slf4j.Slf4j;

/**
 * consumer
 *
 * @author yjlan
 */
@Component
@Slf4j
public class GrabDegreeActivityListener implements MessageListenerConcurrently {
    
    @Resource
    private TopicCourseActivityUserDegreeService topicCourseActivityUserDegreeService;
    
    @Resource
    private RedisCache redisCache;
    
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt messageExt : list) {
                String message = new String(messageExt.getBody());
                log.info("GrabDegreeActivityConsumer message:{}", message);
                GrabDegreeActivityMessage grabDegreeActivityMessage = JSON
                        .parseObject(message, GrabDegreeActivityMessage.class);
                // 保证幂等性
                String key = CacheKeyConstants.buildCheckRepeatKey(grabDegreeActivityMessage.getActivityId(),grabDegreeActivityMessage.getUserId());
                String value = redisCache.get(key);
                if (StringUtils.isNotBlank(value)) {
                    log.info("activityId:{}.userId:{} 重复消费",grabDegreeActivityMessage.getActivityId(),grabDegreeActivityMessage.getUserId());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 保存2小时
                redisCache.set(key,"1",7200);
                
                topicCourseActivityUserDegreeService.saveUserDegree(
                        grabDegreeActivityMessage.getUserId(), grabDegreeActivityMessage.getActivityId(),
                        grabDegreeActivityMessage.getCourseId()
                );
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("consumer error", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
