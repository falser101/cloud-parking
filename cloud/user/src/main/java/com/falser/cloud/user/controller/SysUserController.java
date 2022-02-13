package com.falser.cloud.user.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.entity.SysUserRole;
import com.falser.cloud.user.service.SysUserRoleService;
import com.falser.cloud.user.service.SysUserService;
import com.falser.cloud.user.vo.SysUserInsertOrUpdateVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户信息(SysUser)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@RestController
@RequestMapping("/api/user")
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    public SysUserController(@Lazy SysUserService sysUserService, @Lazy SysUserRoleService sysUserRoleService) {
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
    }

    @GetMapping("{id}")
    @SaCheckPermission(value = {"system:user:detail"}, orRole = {"super_admin"})
    public ApiResponse<SysUser> getUser(@PathVariable String id){
        return ApiResponse.ofSuccess(sysUserService.getById(id));
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping
    @SaCheckPermission(value = {"system:user:list"}, orRole = {"super_admin"})
    public ApiResponse<IPage<SysUser>> selectAll(Page<SysUser> page, SysUser sysUser) {
        return ApiResponse.ofSuccess(this.sysUserService.page(page, new QueryWrapper<>(sysUser)));
    }

    @ApiOperation("新增系统用户")
    @PostMapping
    @SaCheckPermission(value = {"system:user:add"}, orRole = {"super_admin"})
    public ApiResponse<String> insert(@Valid @RequestBody SysUserInsertOrUpdateVO vo){
        this.sysUserService.addSystemUser(vo);
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("修改数据")
    @PutMapping
    @SaCheckPermission(value = {"system:user:update"}, orRole = {"super_admin"})
    public ApiResponse<?> update(@RequestBody SysUser sysUser) {
        return ApiResponse.ofSuccess(this.sysUserService.updateById(sysUser));
    }

    @ApiOperation("删除数据")
    @DeleteMapping("{id}")
    @SaCheckPermission(value = {"system:user:remove"}, orRole = {"super_admin"})
    public ApiResponse<String> delete(@PathVariable Long id) {
        this.sysUserService.removeById(id);
        this.sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, id));
        return ApiResponse.ofSuccess();
    }
}

