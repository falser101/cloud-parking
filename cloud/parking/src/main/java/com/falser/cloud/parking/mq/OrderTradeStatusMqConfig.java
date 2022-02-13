package com.falser.cloud.parking.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderTradeStatusMqConfig {

    @Value("${mq.order.trade.status.exchange}")
    private String Delay_Exchange_Name;

    @Value("${mq.order.trade.status.queue}")
    private String Timeout_Trade_Queue_Name;

    @Value("${mq.order.trade.status.routeKey}")
    private String routeKey;

    @Bean
    public Queue orderTradeStatusQueue() {
        return new Queue(Timeout_Trade_Queue_Name, true);
    }

    // 绑定延时队列与交换机
    @Bean
    public Binding orderTradeStatusBind() {
        return BindingBuilder.bind(orderTradeStatusQueue()).to(orderTradeStatusExchange()).with(routeKey);
    }

    @Bean
    public TopicExchange orderTradeStatusExchange() {
        TopicExchange topicExchange = new TopicExchange(Delay_Exchange_Name, true, false, null);
        topicExchange.setDelayed(true);
        return topicExchange;
    }
}
