package com.falser.cloud.user.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息(SysUser)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@RestController
@RequestMapping("user")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @GetMapping("{id}")
    @SaCheckPermission(value = {"user"}, orRole = {"super_admin"})
    public ApiResponse<SysUser> getUser(@PathVariable String id){
        return ApiResponse.ofSuccess(sysUserService.getById(id));
    }
}

