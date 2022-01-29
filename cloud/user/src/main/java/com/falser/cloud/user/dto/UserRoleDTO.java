package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色dto
 *
 * @author 10235
 * @date 2021/11/22
 */
@Data
public class UserRoleDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色KEY")
    private String roleKey;
}
