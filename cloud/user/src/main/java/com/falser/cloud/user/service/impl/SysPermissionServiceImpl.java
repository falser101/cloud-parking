package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.user.dao.SysPermissionDao;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表(SysPermission)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

}

