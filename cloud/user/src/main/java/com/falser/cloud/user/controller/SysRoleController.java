package com.falser.cloud.user.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.dto.RoleDetailDTO;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.service.SysRoleService;
import com.falser.cloud.user.vo.RoleAddVO;
import com.falser.cloud.user.vo.RoleDetailVO;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色信息表(SysRole)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(@Lazy SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @GetMapping
    @SaCheckPermission(value = "system:role:list", orRole = {"super_admin"})
    public ApiResponse<IPage<SysRole>> selectAll(Page<SysRole> page, SysRole sysRole) {
        return ApiResponse.ofSuccess(this.sysRoleService.page(page, new QueryWrapper<>(sysRole)));
    }

    @GetMapping("{id}")
    @SaCheckPermission(value = "system:role:detail", orRole = {"super_admin"})
    public ApiResponse<RoleDetailDTO> selectOne(@PathVariable Long id) {
        return ApiResponse.ofSuccess(this.sysRoleService.getRoleDetailById(id));
    }

    @PostMapping
    @SaCheckPermission(value = "system:role:add", orRole = {"super_admin"})
    public ApiResponse<String> insert(@Valid @RequestBody RoleAddVO vo) {
        this.sysRoleService.addRole(vo);
        return ApiResponse.ofSuccess();
    }

    @PutMapping
    @SaCheckPermission(value = "system:role:update", orRole = {"super_admin"})
    public ApiResponse<String> update(@Valid @RequestBody RoleDetailVO vo) {
        this.sysRoleService.updateRole(vo);
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping
    @SaCheckPermission(value = "system:role:delete", orRole = {"super_admin"})
    public ApiResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.sysRoleService.removeByIds(idList));
    }
}

