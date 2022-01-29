package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录成功dto
 *
 * @author falser
 * @date 2022/01/29 14:05:55
 */
@Data
@Accessors(chain = true)
public class LoginSuccessDTO {
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("tokenKey")
    private String tokenKey;

    @ApiModelProperty("用户信息")
    private UserDTO userDTO;
}
