package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 停车枚举类型
 *
 * @author zhangjianfei
 * @date 2021/11/30
 */
@Getter
@AllArgsConstructor
public enum ParkingTypeEnum {
    TEMPORARY("临时停车"),
    TOP_UP("充值扣费停车"),
    RENTING("租车位停车"),
    PURCHASE("购买车位停车"),
    ;

    private final String value;
}
