package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户列表dto
 *
 * @author 10235
 * @date 2021/11/22
 */
@Data
public class UserListDTO {

    @ApiModelProperty("id")
    private Long id;
    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    private String loginName;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String userName;
    /**
     * 用户类型（00系统用户 01注册用户）
     */
    @ApiModelProperty("用户类型（00系统用户 01注册用户）")
    private String userType;
    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;
    /**
     * 状态
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 角色dtolist
     */
    private List<UserRoleDTO> roleDTOList;
}
