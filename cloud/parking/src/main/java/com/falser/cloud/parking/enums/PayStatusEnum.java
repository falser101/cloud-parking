package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {
    TRADE_SUCCESS(100, "交易支付成功。"),
    WAIT_BUYER_PAY(101, "交易创建，等待买家付款。"),
    TRADE_CLOSED(102, "未付款交易超时关闭，或支付完成后全额退款。"),
    TRADE_FINISHED(-1, "交易结束，不可退款"),
    ;

    private final Integer code;
    private final String desc;
}
