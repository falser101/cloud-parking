package com.falser.cloud.user.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.falser.cloud.common.dto.ContentDTO;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.dto.MenuTree;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.service.SysPermissionService;
import com.falser.cloud.user.vo.PermissionAddVO;
import com.falser.cloud.user.vo.PermissionUpdateVO;
import com.falser.cloud.user.vo.PermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表(SysPermission)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/api/permission")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;

    @SaCheckPermission("system:permission:list")
    @GetMapping
    @ApiOperation("权限列表")
    public ApiResponse<IPage<MenuTree>> selectAll(PermissionVO vo) {
        return ApiResponse.ofSuccess(sysPermissionService.getPageByVo(vo));
    }

    @GetMapping("{id}")
    @ApiOperation("通过主键查询单条数据")
    public ApiResponse<SysPermission> selectOne(@PathVariable Serializable id) {
        return ApiResponse.ofSuccess(this.sysPermissionService.getById(id));
    }

    @PostMapping
    @ApiOperation("新增数据")
    public ApiResponse<String> insert(@Valid @RequestBody PermissionAddVO vo) {
        this.sysPermissionService.addPermission(vo);
        return ApiResponse.ofSuccess();
    }

    @PutMapping
    @ApiOperation("修改数据")
    public ApiResponse<String> update(@Valid @RequestBody PermissionUpdateVO vo) {
        this.sysPermissionService.updatePermission(vo);
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping
    @ApiOperation("删除数据")
    public ApiResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.sysPermissionService.removeByIds(idList));
    }

    @GetMapping("level1")
    @ApiOperation("查询所有一级菜单或者所有controller")
    public ApiResponse<List<ContentDTO.MenuDTO>> getLevel1MenuOrInterface(@RequestParam PermissionTypeEnum permissionTypeEnum){
        return ApiResponse.ofSuccess(sysPermissionService.getLevel1Permission(permissionTypeEnum));
    }

}

