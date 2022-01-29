package com.falser.cloud.user.vo;

import com.falser.cloud.common.enums.PermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @NotNull(message = "权限名不能为空")
    @ApiModelProperty("权限名")
    private String permissionName;

    @ApiModelProperty("id")
    private String icon;

    @ApiModelProperty("权限标识符")
    private String perms;

    @ApiModelProperty("method")
    private String method;

    @ApiModelProperty("orderNum")
    private Integer orderNum;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("父权限目录id")
    private Long parentId;
}
