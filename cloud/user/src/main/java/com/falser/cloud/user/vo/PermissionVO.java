package com.falser.cloud.user.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 许可签证官
 *
 * @author 10235
 * @date 2021/11/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionVO extends Page{
    private String permissionName;
    private String perms;
    @ApiModelProperty("权限类型")
    private PermissionTypeEnum permissionType;
}
