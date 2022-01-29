package com.falser.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.user.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 菜单权限表(SysPermission)表数据库访问层
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysPermissionDao extends BaseMapper<SysPermission> {

}

