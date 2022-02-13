package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayChannelTypeEnum {

    ALIPAY("支付宝");

    private final String value;
}
