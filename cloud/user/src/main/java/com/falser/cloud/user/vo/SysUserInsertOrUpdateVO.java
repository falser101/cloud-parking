package com.falser.cloud.user.vo;


import com.falser.cloud.common.enums.SexEnum;
import com.falser.cloud.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户信息表(SysUser)表实体类
 *
 * @author makejava
 * @since 2021-09-16 20:49:57
 */
@Data
public class SysUserInsertOrUpdateVO {

    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String userName;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    @NotEmpty(message = "手机号码不能为空")
    private String mobile;

    @ApiModelProperty("用户性别")
    @NotNull(message = "用户性别不能为空")
    private SexEnum sex;

    @ApiModelProperty("头像路径")
    private String avatar;

    @ApiModelProperty("帐号状态")
    private StatusEnum status;

    @ApiModelProperty("备注")
    private String remark;
}

