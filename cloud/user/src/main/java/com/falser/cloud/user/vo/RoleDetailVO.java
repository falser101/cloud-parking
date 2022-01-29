package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色德泰签证官
 *
 * @author 10235
 * @date 2021/11/21
 */
@Data
public class RoleDetailVO {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("角色名")
    @NotEmpty(message = "角色名不能为空")
    private String roleName;

    @ApiModelProperty("角色key")
    @NotEmpty(message = "角色key不能为空")
    private String roleKey;

    @ApiModelProperty("菜单列表")
    @NotEmpty(message = "菜单列表不能为空")
    private List<Long> menuList;

    @ApiModelProperty("接口列表")
    @NotEmpty(message = "接口列表不能为空")
    private List<Long> interfaceList;
}
