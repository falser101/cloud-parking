package com.falser.cloud.message.mq;


import com.alibaba.fastjson.JSON;
import com.falser.cloud.message.dto.MessageCycleCreateDTO;
import com.falser.cloud.message.entity.MessageCyclePlan;
import com.falser.cloud.message.factory.MessageClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageCycleMqConsumer {


    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final MessageClient messageClient;

    public MessageCycleMqConsumer(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    @RabbitListener(queues = "${mq.message.cycle.queue}", containerFactory = "myContainerFactory")
    public void dealMq(String data, Message message, Channel channel) throws IOException {
        log.info("消息中心开始消费周期消息:data:{},tag:{}", data, message.getMessageProperties().getDeliveryTag());
        try {
            MessageCyclePlan plan = JSON.parseObject(data, MessageCyclePlan.class);
            MessageCycleCreateDTO messageCycleCreateDTO = new MessageCycleCreateDTO();
            messageCycleCreateDTO.setPlanId(plan.getId());
            messageCycleCreateDTO.setTitle(plan.getTitle());
            messageCycleCreateDTO.setContent(plan.getContent());
            messageCycleCreateDTO.setType(plan.getType());
            messageClient.handleCycleMessage(messageCycleCreateDTO);
        } catch (Exception e) {
            log.error("消息中心消费周期消息失败!,错误:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
