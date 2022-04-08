package com.grab.degree.activity.config.mq;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

import lombok.extern.slf4j.Slf4j;

/**
 * 事务消息抽象类，发送事务消息要继承该类
 * @author yjlan
 */
@Slf4j
public abstract class AbstractTransactionProducer {

    protected TransactionMQProducer producer;

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            log.error("producer start error", e);
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }

    public TransactionMQProducer getProducer() {
        return producer;
    }

}
