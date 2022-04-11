package com.topic.course.config.consumer;


import javax.annotation.Resource;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grab.degree.common.constants.RocketMqConstants;

/**
 * Consumer Config
 * @author yjlan
 */
@Configuration
public class ConsumerConfig {
    
    @Resource
    private RocketMQProperties rocketmqproperties;
    
    @Bean("grabDegreeActivityConsumer")
    public DefaultMQPushConsumer grabDegreeActivityConsumer(GrabDegreeActivityListener grabDegreeActivityListener)
            throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketMqConstants.GRAB_DEGREE_ACTIVITY_DEFAULT_CONSUMER_GROUP);
        consumer.setNamesrvAddr(rocketmqproperties.getNameServer());
        consumer.subscribe(RocketMqConstants.GRAB_DEGREE_ACTIVITY_PRODUCER_TOPIC, "*");
        consumer.registerMessageListener(grabDegreeActivityListener);
        consumer.start();
        return consumer;
    }
}
