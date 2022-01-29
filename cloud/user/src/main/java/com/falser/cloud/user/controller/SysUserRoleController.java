package com.falser.cloud.user.controller;


import com.falser.cloud.user.entity.SysUserRole;
import com.falser.cloud.user.service.SysUserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户和角色关联表(SysUserRole)表控制层
 *
 * @author falser
 * @since 2022-01-29 00:52:19
 */
@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleService sysUserRoleService;
}

