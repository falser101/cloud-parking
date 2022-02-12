package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRolePermission;

import java.util.List;

/**
 * (SysRolePermission)表服务接口
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {
    /**
     * 获得许可的角色id列表
     *
     * @param roleIds 角色id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> getPermissionListByRoleIds(List<Long> roleIds);

    /**
     * 通过角色id和权限类型获取权限id
     *
     * @param id id
     * @return {@link List}<{@link Long}>
     */
    List<Long> getPermissionIdsByRoleId(Long id);
}

