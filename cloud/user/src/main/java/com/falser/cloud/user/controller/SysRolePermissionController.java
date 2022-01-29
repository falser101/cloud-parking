package com.falser.cloud.user.controller;


import com.falser.cloud.user.entity.SysRolePermission;
import com.falser.cloud.user.service.SysRolePermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysRolePermission)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@RestController
@RequestMapping("sysRolePermission")
public class SysRolePermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysRolePermissionService sysRolePermissionService;
}

