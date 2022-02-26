package com.falser.cloud.message.dto;

import com.falser.cloud.message.enums.MessageCycleEnum;
import lombok.Data;

@Data
public class MessageCycleCreateDTO {
    private Long planId;

    private String title;

    private String content;

    private MessageCycleEnum type;

    private Long userId;
}
