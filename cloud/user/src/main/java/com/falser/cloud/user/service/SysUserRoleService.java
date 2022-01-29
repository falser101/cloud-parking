package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.entity.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表服务接口
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 通过用户id获取权限列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link SysRole}>
     */
    List<SysPermission> getPermissionListByUserId(Long userId);
}

