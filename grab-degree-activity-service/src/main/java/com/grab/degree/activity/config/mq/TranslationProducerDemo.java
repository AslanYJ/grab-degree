package com.grab.degree.activity.config.mq;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 事务消息的实现demo
 * @author yjlan
 */
@Slf4j
@Component
public class TranslationProducerDemo extends AbstractTransactionProducer{
    
    @Autowired
    public TranslationProducerDemo(RocketMQProperties rocketMQProperties) {
        producer = new TransactionMQProducer("demo_group");
        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        start();
    }
}
