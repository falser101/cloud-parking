package com.falser.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.falser.cloud.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息(SysUser)表数据库访问层
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysUserDao extends BaseMapper<SysUser> {

}

