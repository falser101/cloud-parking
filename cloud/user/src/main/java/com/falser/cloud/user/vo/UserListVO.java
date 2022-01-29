package com.falser.cloud.user.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户列表签证官
 *
 * @author 10235
 * @date 2021/11/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListVO extends Page {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String mobile;

}
