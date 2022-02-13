package com.falser.cloud.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.falser.cloud.common.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 数据字典表(SysDataDict)表实体类
 *
 * @author falser
 * @since 2022-02-13 11:30:52
 */
@Data
@TableName("sys_data_dict")
public class SysDataDict implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 父节点ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父节点编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 排序
     */
    @TableField("sort_number")
    private Integer sortNumber;

    /**
     * 状态 1-启用 0-禁用
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 描述
     */
    @TableField("dict_remark")
    private String dictRemark;


    @TableField("update_user_id")
    private Long updateUserId;


    @TableField("update_user_name")
    private String updateUserName;


    @TableField("create_user_id")
    private Long createUserId;


    @TableField("create_user_name")
    private String createUserName;


    @TableField("create_time")
    private LocalDateTime createTime;


    @TableField("update_time")
    private LocalDateTime updateTime;

}

