package com.falser.cloud.message.entity;

import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.enums.TimeCycleEnum;
import lombok.Data;

import java.time.*;

@Data
public class MessageCyclePlan {
    private Long id;

    private String name;

    private MessageCycleEnum type;

    private String title;

    private String content;

    private TimeCycleEnum timeCycle;

    private Month month;

    private Integer day;

    private DayOfWeek dayOfWeek;

    private LocalTime time;

    private LocalDate beginDate;

    private LocalDate endDate;



}
