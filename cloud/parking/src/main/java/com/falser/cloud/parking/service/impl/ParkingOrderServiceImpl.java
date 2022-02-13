package com.falser.cloud.parking.service.impl;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.parking.alipay.AlipayTrade;
import com.falser.cloud.parking.dao.ParkingOrderDao;
import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.enums.PayStatusEnum;
import com.falser.cloud.parking.mq.OrderTradeStatusMqProducer;
import com.falser.cloud.parking.service.ParkingMemberService;
import com.falser.cloud.parking.service.ParkingOrderService;
import com.falser.cloud.parking.service.ParkingParkingSpaceService;
import org.springframework.stereotype.Service;

/**
 * 出场订单主表(ParkingOrder)表服务实现类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Service
public class ParkingOrderServiceImpl extends ServiceImpl<ParkingOrderDao, ParkingOrder> implements ParkingOrderService {

    private final ParkingMemberService parkingMemberService;

    private final ParkingParkingSpaceService parkingParkingSpaceService;

    private final AlipayTrade alipayTrade;

    private final OrderTradeStatusMqProducer orderTradeSuccessUpdateMqProducer;

    public ParkingOrderServiceImpl(ParkingMemberService parkingMemberService, ParkingParkingSpaceService parkingParkingSpaceService, AlipayTrade alipayTrade, OrderTradeStatusMqProducer orderTradeSuccessUpdateMqProducer) {
        this.parkingMemberService = parkingMemberService;
        this.parkingParkingSpaceService = parkingParkingSpaceService;
        this.alipayTrade = alipayTrade;
        this.orderTradeSuccessUpdateMqProducer = orderTradeSuccessUpdateMqProducer;
    }

    @Override
    public PayStatusEnum queryOrderPayStatus(String carNum) throws AlipayApiException {
//        ParkingMember member = parkingMemberService.getOne(new LambdaQueryWrapper<ParkingMember>()
//                .eq(ParkingMember::getLicensePlateNumber, carNum));
//
//        ParkingOrder order = getOne(new LambdaQueryWrapper<ParkingOrder>()
//                .eq(ParkingOrder::getCustomerId, member.getId())
//                .eq(ParkingOrder::getStatus, OrderStatusEnum.NEW.name()));
//
//        AlipayTradeQueryResponse response = alipayTrade.query(order.getOrderNo());
//        if (Objects.equals("TRADE_SUCCESS", response.getTradeStatus())) {
//            // 修改订单
//            ParkingOrderDTO orderDTO = new ParkingOrderDTO();
//            orderDTO.setId(order.getId());
//            orderDTO.setPayStatus(PayStatusEnum.SUCCESS.name());
//            orderDTO.setPaySerialNumber(response.getTradeNo());
//            orderDTO.setPayTime(LocalDateTime.ofInstant(response.getSendPayDate().toInstant(), ZoneId.systemDefault()));
//            orderDTO.setPayStatus(PayStatusEnum.SUCCESS.name());
//            // 发送mq修改订单
////            orderTradeSuccessUpdateMqProducer.sendMsg(orderDTO);
//            return PayStatusEnum.SUCCESS;
//            // 车辆放行
//        } else if (Objects.equals("TRADE_SUCCESS", response.getTradeStatus())) {
//            order.setStatus(OrderStatusEnum.COMPLETED.name());
//            order.setPayStatus(PayStatusEnum.SUCCESS.name());
//        }
        return null;
    }
}

