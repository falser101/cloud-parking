package com.falser.cloud.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.falser.cloud.common.enums.DeleteEnum;
import com.falser.cloud.common.enums.SexEnum;
import com.falser.cloud.common.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户信息(SysUser)表实体类
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @TableField("username")
    private String username;

    /**
     * 用户类型(SYS_USER系统用户 REGISTER_USER注册用户)
     */
    @TableField("user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户性别 MALE男 FEMALE女 UNKNOWN未知
     */
    @TableField("sex")
    private SexEnum sex;

    /**
     * 头像路径
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 帐号状态   DISABLE停用 ENABLE启用
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 删除标志 DELETED删除 UN_DELETED未删除
     */
    @TableField("del_flag")
    private DeleteEnum delFlag;

    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_date")
    private LocalDateTime loginDate;

    /**
     * 密码最后更新时间
     */
    @TableField("pwd_update_date")
    private LocalDateTime pwdUpdateDate;

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

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}

