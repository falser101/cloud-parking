package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsingStatusEnum {
    IN_USE("使用中"),
    UNUSED("未使用")
    ;
    private final String value;
}
