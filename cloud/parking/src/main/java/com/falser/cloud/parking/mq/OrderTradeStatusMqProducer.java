package com.falser.cloud.parking.mq;

import com.alibaba.fastjson.JSON;
import com.falser.cloud.parking.mq.dto.ParkingOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderTradeStatusMqProducer {

    @Value("${mq.order.trade.status.exchange}")
    private String exchangeName;

    @Value("${mq.order.trade.status.routeKey}")
    private String routeKey;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param dto dto
     */
    public void sendMsg(ParkingOrderDTO dto) {
        String json = JSON.toJSONString(dto);
        log.info("异步发送订单支付状态查询消息MQ,ParkingOrderDTO:{}", json);
        rabbitTemplate.convertAndSend(exchangeName, routeKey, json, message -> {
            message.getMessageProperties().setDelay(5 * 1000); // 5秒
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);  //消息持久化
            return message;
        });
    }
}
