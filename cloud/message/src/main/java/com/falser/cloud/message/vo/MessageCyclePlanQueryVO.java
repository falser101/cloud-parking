package com.falser.cloud.message.vo;


import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.enums.TimeCycleEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;


/**
 * 周期消息计划(MessageCyclePlan)查询VO
 *
 * @author falser
 * @since 2022-02-27 20:32:29
 */
@Data
public class MessageCyclePlanQueryVO implements Serializable {

    @ApiModelProperty("${column.comment}")
    private Long id;


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


    @ApiModelProperty("时间点")
    private String time;


    @ApiModelProperty("开始时间")
    private LocalDateTime beginDate;


    @ApiModelProperty("结束时间")
    private LocalDateTime endDate;


    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;


    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


    @ApiModelProperty("创建人")
    private Long createBy;


    @ApiModelProperty("更新人")
    private Long updateBy;

}

