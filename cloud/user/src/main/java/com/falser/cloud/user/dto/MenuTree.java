package com.falser.cloud.user.dto;

import com.falser.cloud.common.enums.PermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单树
 *
 * @author 10235
 * @date 2021/11/20
 */
@Data
public class MenuTree {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("权限名")
    private String permName;

    @ApiModelProperty("路由名")
    private String routerName;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("权限类型")
    private PermissionTypeEnum permissionType;

    @ApiModelProperty("权限key")
    private String perms;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("children")
    private List<MenuTree> children;

}
