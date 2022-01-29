package com.falser.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.user.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysRoleDao extends BaseMapper<SysRole> {

}

