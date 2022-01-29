package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    @ApiModelProperty("登录密码")
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
