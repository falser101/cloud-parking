package com.falser.cloud.message.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageCycleMqConfig {

    @Value("${mq.message.cycle.exchange}")
    private String Delay_Exchange_Name;

    @Value("${mq.message.cycle.queue}")
    private String Timeout_Trade_Queue_Name;

    @Value("${mq.message.cycle.routeKey}")
    private String routeKey;

    @Bean
    public Queue messageCycleQueue() {
        return new Queue(Timeout_Trade_Queue_Name, true);
    }

    // 绑定延时队列与交换机
    @Bean
    public Binding messageCycleBind() {
        return BindingBuilder.bind(messageCycleQueue()).to(messageCycleExchange()).with(routeKey);
    }

    @Bean
    public TopicExchange messageCycleExchange() {
        TopicExchange topicExchange = new TopicExchange(Delay_Exchange_Name, true, false, null);
        topicExchange.setDelayed(true);
        return topicExchange;
    }
}
