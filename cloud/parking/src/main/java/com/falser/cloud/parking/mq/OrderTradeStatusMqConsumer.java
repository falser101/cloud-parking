package com.falser.cloud.parking.mq;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.falser.cloud.parking.alipay.AlipayTrade;
import com.falser.cloud.parking.entity.ParkingMember;
import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.entity.ParkingParkingSpace;
import com.falser.cloud.parking.enums.OrderStatusEnum;
import com.falser.cloud.parking.enums.PayStatusEnum;
import com.falser.cloud.parking.enums.UsingStatusEnum;
import com.falser.cloud.parking.mq.dto.ParkingOrderDTO;
import com.falser.cloud.parking.service.ParkingMemberService;
import com.falser.cloud.parking.service.ParkingOrderService;
import com.falser.cloud.parking.service.ParkingParkingSpaceService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class OrderTradeStatusMqConsumer {

    private final ParkingOrderService parkingOrderService;

    private final ParkingMemberService parkingMemberService;

    private final ParkingParkingSpaceService parkingParkingSpaceService;

    private final OrderTradeStatusMqProducer orderTradeStatusMqProducer;

    private final AlipayTrade alipayTrade;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public OrderTradeStatusMqConsumer(ParkingOrderService parkingOrderService,
                                      ParkingMemberService parkingMemberService,
                                      ParkingParkingSpaceService parkingParkingSpaceService,
                                      OrderTradeStatusMqProducer orderTradeStatusMqProducer,
                                      AlipayTrade alipayTrade) {
        this.parkingOrderService = parkingOrderService;
        this.parkingMemberService = parkingMemberService;
        this.parkingParkingSpaceService = parkingParkingSpaceService;
        this.orderTradeStatusMqProducer = orderTradeStatusMqProducer;
        this.alipayTrade = alipayTrade;
    }

    @RabbitListener(queues = "${mq.order.trade.status.queue}", containerFactory = "myContainerFactory")
    public void dealMq(String data, Message message, Channel channel) throws IOException {
        log.info("停车后台开始消费订单支付成功消息:data:{},tag:{}", data, message.getMessageProperties().getDeliveryTag());
        try {
            ParkingOrderDTO orderDTO = objectMapper.readValue(data, ParkingOrderDTO.class);
            ParkingOrder order = parkingOrderService.getById(orderDTO.getId());
            ParkingMember member = parkingMemberService.getById(order.getCustomerId());
            ParkingParkingSpace parkingSpace = parkingParkingSpaceService.getOne(new LambdaQueryWrapper<ParkingParkingSpace>().eq(ParkingParkingSpace::getParkingSpaceNum, member.getParkingSpaceNum()));
            if (orderDTO.getTimes().equals(20)) {
                // 撤销交易
                alipayTrade.tradeCancel(order.getOrderNo());
                order.setStatus(OrderStatusEnum.CANCEL);
                order.setPayStatus(PayStatusEnum.TRADE_CLOSED);
                parkingOrderService.updateById(order);
                return;
            }
            AlipayTradeQueryResponse response = alipayTrade.query(order.getOrderNo());
            log.info("交易信息:{}", JSON.toJSONString(response));
            if (Objects.equals(PayStatusEnum.TRADE_SUCCESS.name(), response.getTradeStatus())) {
                order.setPayStatus(PayStatusEnum.TRADE_SUCCESS);
                order.setStatus(OrderStatusEnum.COMPLETED);
                // 释放车位
                parkingSpace.setUsingStatus(UsingStatusEnum.UNUSED);
            } else if (Objects.equals(PayStatusEnum.WAIT_BUYER_PAY.name(), response.getTradeStatus())) {
                order.setPayStatus(PayStatusEnum.WAIT_BUYER_PAY);
                order.setStatus(OrderStatusEnum.ONGOING);
                orderTradeStatusMqProducer.sendMsg(ParkingOrderDTO.builder()
                        .id(orderDTO.getId())
                        .times(orderDTO.getTimes() + 1)
                        .build());
            } else if (Objects.equals(PayStatusEnum.TRADE_CLOSED.name(), response.getTradeStatus())) {
                order.setPayStatus(PayStatusEnum.TRADE_CLOSED);
                order.setStatus(OrderStatusEnum.CLOSED);
            } else if (Objects.equals(PayStatusEnum.TRADE_FINISHED.name(), response.getTradeStatus())) {
                order.setPayStatus(PayStatusEnum.TRADE_FINISHED);
                order.setStatus(OrderStatusEnum.COMPLETED);
                // 释放车位
                parkingSpace.setUsingStatus(UsingStatusEnum.UNUSED);
            } else {
                orderTradeStatusMqProducer.sendMsg(ParkingOrderDTO.builder()
                        .id(orderDTO.getId())
                        .times(orderDTO.getTimes() + 1)
                        .build());
            }
            parkingOrderService.updateById(order);
            parkingParkingSpaceService.updateById(parkingSpace);
        } catch (Exception e) {
            log.error("CRM消费订单支付成功消息失败!,错误:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
