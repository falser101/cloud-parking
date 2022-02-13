package com.falser.cloud.user.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.common.enums.RedisKeyEnum;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * stp接口impl
 * 用于获取当前账号权限码集合
 *
 * @author 10235
 * @date 2021/11/14
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    private final SysUserRoleService sysUserRoleService;

    public StpInterfaceImpl(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<String> getPermissionList(Object loginId, String loginType) {
        String cacheKey = RedisKeyEnum.LoginKeyEnum.LOGIN_PERMISSIONS_KEY.getCacheName() + StpUtil.getTokenValueByLoginId(loginId);
        List<String> sysPermissions = (List<String>) StpUtil.getSession().get(cacheKey);
        if (CollectionUtils.isEmpty(sysPermissions)) {
            // 查库
            log.info("用户id:{}", loginId);
            List<SysPermission> permissionList = sysUserRoleService.getPermissionListByUserId(Long.valueOf(loginId.toString()));
            List<SysPermission> contents = permissionList.stream()
                    .filter(perm -> Objects.equals(perm.getPermissionType(), PermissionTypeEnum.CONTENTS))
                    .collect(Collectors.toList());
            List<SysPermission> menus = permissionList.stream()
                    .filter(perm -> Objects.equals(perm.getPermissionType(), PermissionTypeEnum.MENU))
                    .collect(Collectors.toList());
            List<SysPermission> buttons = permissionList.stream()
                    .filter(perm -> Objects.equals(perm.getPermissionType(), PermissionTypeEnum.BUTTON))
                    .collect(Collectors.toList());

            List<String> collect = buttons.stream()
                    .map(button -> getPerm(contents, menus, button))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            StpUtil.getSession().set(cacheKey, collect);
            return collect;
        }
        return sysPermissions;
    }

    private String getPerm(List<SysPermission> contents, List<SysPermission> menus, SysPermission button) {
        SysPermission buttonMenu = menus.stream()
                .filter(menu -> Objects.equals(menu.getId(), button.getParentId()))
                .findFirst().orElse(null);

        if (Objects.isNull(buttonMenu)) {
            return null;
        }

        SysPermission menuContent = contents.stream()
                .filter(content -> Objects.equals(buttonMenu.getParentId(), content.getId()))
                .findFirst().orElse(null);

        if (Objects.isNull(menuContent)) {
            return null;
        }

        return menuContent.getRouterName() + ":" + buttonMenu.getRouterName() + ":" + button.getPerms();
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<String> getRoleList(Object loginId, String loginType) {
        String cacheKey = RedisKeyEnum.LoginKeyEnum.LOGIN_ROLES_KEY.getCacheName() + StpUtil.getTokenValueByLoginId(loginId);
        List<String> roles = (List<String>) StpUtil.getSession()
                .get(cacheKey);
        if (CollectionUtils.isEmpty(roles)) {
            // 查库
            log.info("用户id:{}", loginId);
            SysRole role = sysUserRoleService.getRoleByUserId(Long.valueOf(loginId.toString()));
            List<String> roleList = List.of(Optional.ofNullable(role)
                    .orElse(SysRole.builder().roleKey("visitor").build()).getRoleKey());
            StpUtil.getSession().set(cacheKey, roleList);
            return roleList;
        }
        return roles;
    }
}
