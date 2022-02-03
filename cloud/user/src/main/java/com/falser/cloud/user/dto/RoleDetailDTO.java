package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色细节dto
 *
 * @author 10235
 * @date 2021/11/21
 */
@Data
public class RoleDetailDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色key")
    private String roleKey;

    @ApiModelProperty("权限列表")
    private List<Long> permList;
}
