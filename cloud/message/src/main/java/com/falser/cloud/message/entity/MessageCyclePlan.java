package com.falser.cloud.message.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.enums.TimeCycleEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 周期消息计划(MessageCyclePlan)表实体类
 *
 * @author falser
 * @since 2022-02-27 20:32:29
 */
@Data
@TableName("message_cycle_plan")
public class MessageCyclePlan implements Serializable {

    @TableField("id")
    private Long id;

    /**
     * 内容
     */
    @TableField("name")
    private String name;

    /**
     * 类型
     */
    @TableField("type")
    private MessageCycleEnum type;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 时间周期
     */
    @TableField("time_cycle")
    private TimeCycleEnum timeCycle;

    /**
     * 月份
     */
    @TableField("month")
    private String month;

    /**
     * 每月的第几天
     */
    @TableField("day")
    private Integer day;

    /**
     * 周几
     */
    @TableField("day_of_week")
    private String dayOfWeek;


    @TableField("time")
    private String time;

    /**
     * 开始时间
     */
    @TableField("begin_date")
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    @TableField("end_date")
    private LocalDateTime endDate;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

}

