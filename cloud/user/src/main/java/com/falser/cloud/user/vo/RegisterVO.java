package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterVO {
    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不为空")
    private String loginName;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不为空")
    private String userName;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不为空")
    private String mobile;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不为空")
    private String password;

    @ApiModelProperty("重复密码")
    @NotBlank(message = "再次输入密码不为空")
    private String rePassword;

}
