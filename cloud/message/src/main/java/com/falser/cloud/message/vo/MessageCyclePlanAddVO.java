package com.falser.cloud.message.vo;


import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.enums.TimeCycleEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;


/**
 * 周期消息计划(MessageCyclePlan)新增VO
 *
 * @author falser
 * @since 2022-02-27 20:32:29
 */
@Data
public class MessageCyclePlanAddVO implements Serializable {

    @ApiModelProperty("内容")
    private String name;


    @ApiModelProperty("类型")
    private MessageCycleEnum type;


    @ApiModelProperty("标题")
    private String title;


    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("时间周期")
    private TimeCycleEnum timeCycle;


    @ApiModelProperty("月份")
    private String month;


    @ApiModelProperty("每月的第几天")
    private Integer day;


    @ApiModelProperty("周几")
    private DayOfWeek dayOfWeek;


    @ApiModelProperty("时间")
    private String time;


    @ApiModelProperty("开始时间")
    private LocalDateTime beginDate;


    @ApiModelProperty("结束时间")
    private LocalDateTime endDate;

}

