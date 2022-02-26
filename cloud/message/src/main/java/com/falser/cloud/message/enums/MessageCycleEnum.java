package com.falser.cloud.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCycleEnum {

    TEST("", ""),
    NORMAL("", ""),
    ;

    private final String wxTemplate;

    private final String smsTemplate;

}
