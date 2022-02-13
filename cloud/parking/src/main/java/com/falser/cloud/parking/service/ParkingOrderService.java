package com.falser.cloud.parking.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.enums.PayStatusEnum;

/**
 * 出场订单主表(ParkingOrder)表服务接口
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
public interface ParkingOrderService extends IService<ParkingOrder> {

    /**
     * 查询订单支付状态
     *
     * @param carNum 汽车num
     * @return {@link PayStatusEnum}
     */
    PayStatusEnum queryOrderPayStatus(String carNum) throws AlipayApiException;
}

