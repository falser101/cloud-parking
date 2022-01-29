package com.falser.cloud.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 菜单权限表(SysPermission)表实体类
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路由名称
     */
    @TableField("router_name")
    private String routerName;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单类型（1目录 2菜单 3按钮）
     */
    @TableField("permission_type")
    private PermissionTypeEnum permissionType;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

}

