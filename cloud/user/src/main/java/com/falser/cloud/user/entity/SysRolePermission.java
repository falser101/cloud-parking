package com.falser.cloud.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * (SysRolePermission)表实体类
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
@Data
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {
    /**
     * 权限id
     */
    @TableField("permission_id")
    private Long permissionId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

}

