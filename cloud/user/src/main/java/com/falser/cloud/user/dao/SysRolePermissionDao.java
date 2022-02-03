package com.falser.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.user.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysRolePermission)表数据库访问层
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysRolePermissionDao extends BaseMapper<SysRolePermission> {

    /**
     * 按照角色id和类型权限id列表
     *
     * @param roleId 角色id
     * @param type   类型
     * @return {@link List}<{@link Long}>
     */
    List<Long> permissionIdListByRoleIdAndType(@Param("roleId") Long roleId, @Param("type") String type);
}

