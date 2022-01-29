package com.falser.cloud.user.dto;

import com.falser.cloud.common.enums.SexEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详细dto
 *
 * @author 10235
 * @date 2021/11/28
 */
@Data
public class UserDetailDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("登录账号")
    private String loginName;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("用户类型（00系统用户 01注册用户）")
    private String userType;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private SexEnum sex;

    @ApiModelProperty("头像路径")
    private String avatar;

    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("最后登录IP")
    private String loginIp;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginDate;

    @ApiModelProperty("密码最后更新时间")
    private LocalDateTime pwdUpdateDate;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("角色列表")
    private List<RoleDTO> roles;
}
