package com.falser.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表数据库访问层
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
    List<SysPermission> getPermissionListByUserId(Long roleId);

    SysRole selectRoleByUserId(Long userId);
}

