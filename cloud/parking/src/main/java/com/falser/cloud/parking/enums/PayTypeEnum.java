package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    ON_LINE("线上支付"),
    OFF_LINE("线下支付"),
    CARD_BALANCE("卡余额支付")
    ;

    private final String value;
}
