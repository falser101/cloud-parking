package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.user.dao.SysUserRoleDao;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysUserRole;
import com.falser.cloud.user.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Override
    public List<SysPermission> getPermissionListByUserId(Long userId) {
        SysUserRole userRole = getOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        return baseMapper.getPermissionListByUserId(userRole.getRoleId());
    }
}

