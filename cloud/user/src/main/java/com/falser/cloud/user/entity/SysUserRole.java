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
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

}

