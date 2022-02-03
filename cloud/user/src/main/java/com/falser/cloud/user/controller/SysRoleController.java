package com.falser.cloud.user.controller;


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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
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
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysRole 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResponse<IPage<SysRole>> selectAll(Page<SysRole> page, SysRole sysRole) {
        return ApiResponse.ofSuccess(this.sysRoleService.page(page, new QueryWrapper<>(sysRole)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResponse<RoleDetailDTO> selectOne(@PathVariable Long id) {
        return ApiResponse.ofSuccess(this.sysRoleService.getRoleDetailById(id));
    }

    /**
     * 新增数据
     *
     * @param vo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResponse<String> insert(@Valid @RequestBody RoleAddVO vo) {
        this.sysRoleService.addRole(vo);
        return ApiResponse.ofSuccess();
    }

    /**
     * 更新
     * 修改数据
     *
     * @param vo 签证官
     * @return 修改结果
     */
    @PutMapping
    public ApiResponse<String> update(@Valid @RequestBody RoleDetailVO vo) {
        this.sysRoleService.updateRole(vo);
        return ApiResponse.ofSuccess();
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.sysRoleService.removeByIds(idList));
    }
}

