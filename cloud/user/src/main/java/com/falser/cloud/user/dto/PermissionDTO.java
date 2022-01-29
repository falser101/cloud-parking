package com.falser.cloud.user.dto;

import com.falser.cloud.common.enums.PermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("权限名")
    private String permissionName;

    @ApiModelProperty("父权限id")
    private Long parentId;

    @ApiModelProperty("菜单类型")
    private PermissionTypeEnum permissionType;

    @ApiModelProperty("方法")
    private String method;
}
