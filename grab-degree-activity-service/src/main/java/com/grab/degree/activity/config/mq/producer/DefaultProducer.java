package com.grab.degree.activity.config.mq.producer;

import java.nio.charset.StandardCharsets;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.stereotype.Component;

import com.grab.degree.common.constants.RocketMqConstants;
import com.grab.degree.common.exception.BaseBizException;
import com.grab.degree.common.exception.BizCodeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息发送
 * @author yjlan
 */
@Slf4j
@Component
public class DefaultProducer {

    private final DefaultMQProducer producer;

    public DefaultProducer(RocketMQProperties rocketMQProperties) {
        producer = new TransactionMQProducer(RocketMqConstants.GRAB_DEGREE_ACTIVITY_DEFAULT_PRODUCER_GROUP);
        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        start();
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    private void start() {
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

    /**
     * 发送消息
     *
     * @param topic   topic
     * @param message 消息
     */
    public void sendMessage(String topic, String message, String type, String tags, String keys) {
        sendMessage(topic, message, -1, type, tags, keys);
    }

    /**
     * 发送消息
     *
     * @param topic   topic
     * @param message 消息
     */
    public void sendMessage(String topic, String message, Integer delayTimeLevel, String type, String tags, String keys) {
        Message msg = new Message(topic,tags,keys,message.getBytes(StandardCharsets.UTF_8));
        try {
            if (delayTimeLevel > 0) {
                msg.setDelayTimeLevel(delayTimeLevel);
            }
            SendResult send = producer.send(msg);
            if (SendStatus.SEND_OK == send.getSendStatus()) {
                log.info("发送MQ消息成功, type:{}, message:{}", type, message);
            } else {
                throw new BaseBizException(send.getSendStatus().toString());
            }
        } catch (Exception e) {
            log.error("发送MQ消息失败：", e);
            throw new BaseBizException(BizCodeEnum.MQ_SEND_FAIL.getErrorCode(),
                    BizCodeEnum.MQ_SEND_FAIL.getErrorMsg());
        }
    }
    
    public void sendOrderMessage(String topic, String message, Integer delayTimeLevel,
            String type, String tags, String keys,Long userId) {
        Message msg = new Message(topic, tags, keys, message.getBytes(StandardCharsets.UTF_8));
        try {
            if (delayTimeLevel > 0) {
                msg.setDelayTimeLevel(delayTimeLevel);
            }
            SendResult send = producer.send(msg, (list, message1, o) -> {
                Long userId1 = (Long) o;
                long index = userId1 % list.size();
                return list.get((int) index);
            }, userId);
            if (SendStatus.SEND_OK == send.getSendStatus()) {
                log.info("发送MQ消息成功, type:{}, message:{}", type, message);
            } else {
                throw new BaseBizException(send.getSendStatus().toString());
            }
        } catch (Exception e) {
            log.error("发送MQ消息失败：", e);
            throw new BaseBizException(BizCodeEnum.MQ_SEND_FAIL.getErrorCode(),
                    BizCodeEnum.MQ_SEND_FAIL.getErrorMsg());
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}
