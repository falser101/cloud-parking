package com.falser.cloud.message.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 消息数据表(Message)表实体类
 *
 * @author falser
 * @since 2022-02-26 11:33:44
 */
@Data
@TableName("message")
public class Message implements Serializable {
    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

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
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 消息类型枚举
     */
    @TableField("type")
    private String type;

    /**
     * 图片
     */
    @TableField("images")
    private String images;

    /**
     * 租户id
     */
    @TableField("space_id")
    private Long spaceId;

}

