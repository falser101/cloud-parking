package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录用户信息dto
 *
 * @author 10235
 * @date 2021/11/21
 */
@Data
public class LoginUserInfoDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("备注")
    private String remark;
}
