package com.falser.cloud.parking.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.falser.cloud.common.enums.RedisKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> sysPermissions = (List<String>) StpUtil.getSession().get(RedisKeyEnum.LoginKeyEnum.LOGIN_PERMISSIONS_KEY + StpUtil.getTokenValueByLoginId(loginId));
        if (CollectionUtils.isEmpty(sysPermissions)) {
            return new ArrayList<>();
        }
        return sysPermissions;
    }

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = (List<String>) StpUtil.getSession().get(RedisKeyEnum.LoginKeyEnum.LOGIN_ROLES_KEY + StpUtil.getTokenValueByLoginId(loginId));
        if (CollectionUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }
        return roles;
    }
}
