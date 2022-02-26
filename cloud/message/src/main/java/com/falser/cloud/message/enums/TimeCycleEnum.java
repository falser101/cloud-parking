package com.falser.cloud.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeCycleEnum {
    YEAR("每年"),
    MONTH("每月"),
    WEEK("每周"),
    DAY("每天")
    ;

    private final String value;
}
