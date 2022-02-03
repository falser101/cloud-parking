package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.user.dao.SysRolePermissionDao;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRolePermission;
import com.falser.cloud.user.service.SysPermissionService;
import com.falser.cloud.user.service.SysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (SysRolePermission)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionDao, SysRolePermission> implements SysRolePermissionService {

    private final SysPermissionService sysPermissionService;

    public SysRolePermissionServiceImpl(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public List<SysPermission> getPermissionListByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<SysRolePermission>()
                .in(SysRolePermission::getRoleId, roleIds);
        List<SysRolePermission> rolePermissions = list(queryWrapper);
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return new ArrayList<>();
        }
        List<Long> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        return Optional.of(sysPermissionService.listByIds(permissionIds)).orElse(new ArrayList<>());
    }

    @Override
    public List<Long> getPermissionIdsByRoleIdAndType(Long id, PermissionTypeEnum permissionTypeEnum) {
        return baseMapper.permissionIdListByRoleIdAndType(id, permissionTypeEnum.name());
    }
}

