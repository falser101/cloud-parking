package com.falser.cloud.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    DEFAULT("DEFAULT", "默认消息"),
    SYSTEM("SYSTEM", "系统消息"),

    ;

    private final String key;
    private final String value;
}
