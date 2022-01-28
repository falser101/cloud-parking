package com.falser.cloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {
    MALE("男", 0),
    FEMALE("女", 1),
    UNKNOWN("未知", 2),
    ;

    private final String value;
    private final Integer code;
}
