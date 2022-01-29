package com.falser.cloud.user.controller;


import com.falser.cloud.user.service.SysPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单权限表(SysPermission)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@RestController
@RequestMapping("sysPermission")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;
}

