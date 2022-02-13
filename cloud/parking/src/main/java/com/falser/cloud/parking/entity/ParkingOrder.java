package com.falser.cloud.parking.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.parking.enums.OrderStatusEnum;
import com.falser.cloud.parking.enums.PayChannelTypeEnum;
import com.falser.cloud.parking.enums.PayStatusEnum;
import com.falser.cloud.parking.enums.PayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 出场订单主表(ParkingOrder)表实体类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Data
@TableName("parking_order")
public class ParkingOrder {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 下单人ID
     */
    @TableField("customer_id")
    private Long customerId;

    /**
     * 支付方式：线下支付、线上付款、卡余额扣款等
     */
    @TableField("pay_type")
    private PayTypeEnum payType;

    /**
     * 支付渠道
     */
    @TableField("pay_channel")
    private PayChannelTypeEnum payChannel;

    /**
     * 订单金额
     */
    @TableField("order_money")
    private BigDecimal orderMoney;

    /**
     * 优惠金额
     */
    @TableField("discount_money")
    private BigDecimal discountMoney;

    /**
     * 应付金额
     */
    @TableField("payable_amount")
    private BigDecimal payableAmount;

    /**
     * 实付金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private LocalDateTime payTime;

    /**
     * 订单状态
     */
    @TableField("status")
    private OrderStatusEnum status;

    /**
     * 订单备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 付款流水号
     */
    @TableField("pay_serial_number")
    private String paySerialNumber;

    /**
     * 删除 1是 0否
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 订单客户类型:普通、大客户
     */
    @TableField("order_customer_type")
    private String orderCustomerType;

    /**
     * 付款状态
     */
    @TableField("pay_status")
    private PayStatusEnum payStatus;

    /**
     * 支付宝二维码
     */
    @TableField("alipay_qr_code")
    private String alipayQrCode;

}

