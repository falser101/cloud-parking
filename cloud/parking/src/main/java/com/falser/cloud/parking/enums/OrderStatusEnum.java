package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    NEW("新建"),
    ONGOING("进行中"),
    COMPLETED("已完成"),
    CANCEL("交易取消"),
    CLOSED("已关闭")

    ;
    private final String value;
}
