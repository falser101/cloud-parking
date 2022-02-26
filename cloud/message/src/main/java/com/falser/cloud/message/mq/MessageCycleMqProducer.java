package com.falser.cloud.message.mq;

import com.alibaba.fastjson.JSON;
import com.falser.cloud.message.entity.MessageCyclePlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MessageCycleMqProducer {
    @Value("${mq.message.cycle.exchange}")
    private String exchangeName;

    @Value("${mq.message.cycle.routeKey}")
    private String routeKey;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param plan 计划
     */
    public void sendMsg(MessageCyclePlan plan) {
        String json = JSON.toJSONString(plan);
        log.info("周期计划:{}", json);
        rabbitTemplate.convertAndSend(exchangeName, routeKey, json, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);  //消息持久化
            return message;
        });
    }
}
