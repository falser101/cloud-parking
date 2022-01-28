package com.falser.cloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteEnum {
    DELETED("删除", 0),
    UN_DELETED("未删除", 1);

    private final String value;
    private final Integer code;
}
