package com.falser.cloud.user.vo;

import com.falser.cloud.common.enums.PermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 允许更新签证官
 *
 * @author 10235
 * @date 2021/11/20
 */
@Data
public class PermissionUpdateVO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Long id;

    @NotNull(message = "权限类型不能为空")
    @ApiModelProperty("权限类型")
    private PermissionTypeEnum permissionType;

    @ApiModelProperty("权限名")
    @NotBlank(message = "权限名不能为空")
    private String permName;

    @ApiModelProperty("路由名")
    private String routerName;

    @ApiModelProperty("权限标识符")
    private String perms;

    @ApiModelProperty("父权限目录id")
    private Long parentId;
}
