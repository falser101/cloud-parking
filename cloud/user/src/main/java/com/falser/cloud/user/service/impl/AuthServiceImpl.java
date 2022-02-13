package com.falser.cloud.user.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.falser.cloud.common.dto.ContentDTO;
import com.falser.cloud.common.dto.UserInfo;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.common.enums.RequestStatus;
import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.user.dto.LoginSuccessDTO;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.entity.SysUserRole;
import com.falser.cloud.user.service.AuthService;
import com.falser.cloud.user.service.SysRoleService;
import com.falser.cloud.user.service.SysUserRoleService;
import com.falser.cloud.user.service.SysUserService;
import com.falser.cloud.user.vo.LoginVO;
import com.falser.cloud.user.vo.RegisterVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 身份验证服务impl
 *
 * @author falser
 * @date 2022/01/29 15:25:22
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    private final SysRoleService sysRoleService;

    public AuthServiceImpl(@Lazy PasswordEncoder passwordEncoder,
                           @Lazy SysUserService sysUserService,
                           @Lazy SysUserRoleService sysUserRoleService,
                           @Lazy SysRoleService sysRoleService) {
        this.passwordEncoder = passwordEncoder;
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
    }

    @Override
    public LoginSuccessDTO login(LoginVO vo) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getLoginName, vo.getLoginName())
                .eq(SysUser::getStatus, StatusEnum.ENABLE.name());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (Objects.nonNull(one)) {
            if (passwordEncoder.matches(vo.getPassword(), one.getPassword())) {
                StpUtil.login(one.getId(), new SaLoginModel().setTimeout(60 * 60 * 24 * 7));
                one.setLoginIp(StpUtil.getLoginDevice());
                one.setLoginDate(LocalDateTime.now());
                sysUserService.updateById(one);
                return new LoginSuccessDTO()
                        .setToken(StpUtil.getTokenValue())
                        .setTokenKey(StpUtil.getTokenName());
            }
            throw new BaseException(RequestStatus.USERNAME_PASSWORD_ERROR);
        }
        throw new BaseException(RequestStatus.USER_NOT_REGISTER_ERROR);
    }

    @Override
    public UserInfoDTO getUserInfo() {
        Long userId = StpUtil.getLoginId(0L);
        // userinfo
        SysUser sysUser = sysUserService.getById(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(sysUser, userInfo);
        userInfoDTO.setUserInfo(userInfo);

        // 角色信息
        SysRole sysRole = getSysRole(userId);
        userInfoDTO.setRoles(List.of(Optional.ofNullable(sysRole)
                .orElse(SysRole.builder().roleKey("visitor").build())
                .getRoleKey()));

        // 权限信息
        List<ContentDTO> contents = getContentDTOS(userId);
        userInfoDTO.setContents(contents);
        StpUtil.getSession().set("userinfo", userInfoDTO);
        return userInfoDTO;
    }

    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return {@link SysRole}
     */
    private SysRole getSysRole(Long userId) {
        SysUserRole one = sysUserRoleService.getOne(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
        return sysRoleService.getById(one.getRoleId());
    }

    /**
     * 获取内容dto
     *
     * @param userId 用户id
     * @return {@link List}<{@link ContentDTO}>
     */
    private List<ContentDTO> getContentDTOS(Long userId) {
        List<SysPermission> permissionList = sysUserRoleService.getPermissionListByUserId(userId);
        if (CollectionUtils.isEmpty(permissionList)) {
            return Collections.emptyList();
        }
        Map<PermissionTypeEnum, List<SysPermission>> permMap = permissionList.stream()
                .collect(Collectors.groupingBy(SysPermission::getPermissionType));

        List<SysPermission> menus = permMap.getOrDefault(PermissionTypeEnum.MENU, Collections.emptyList());
        List<SysPermission> buttons = permMap.getOrDefault(PermissionTypeEnum.BUTTON, Collections.emptyList());

        return permMap.getOrDefault(PermissionTypeEnum.CONTENTS, Collections.emptyList())
                .stream()
                .map(content -> {
                    ContentDTO contentDTO = new ContentDTO();
                    BeanUtils.copyProperties(content, contentDTO);
                    List<ContentDTO.MenuDTO> menuDTOS = dealMenus(menus, buttons, content);
                    contentDTO.setMenus(menuDTOS);
                    return contentDTO;
                }).collect(Collectors.toList());
    }

    /**
     * 处理菜单
     *
     * @param menus   菜单
     * @param buttons 按钮
     * @param content 内容
     * @return {@link List}<{@link ContentDTO.MenuDTO}>
     */
    private List<ContentDTO.MenuDTO> dealMenus(List<SysPermission> menus, List<SysPermission> buttons, SysPermission content) {
        return menus.stream()
                .filter(menu -> Objects.equals(menu.getParentId(), content.getId()))
                .map(menu -> {
                    ContentDTO.MenuDTO menuDTO = new ContentDTO.MenuDTO();
                    BeanUtils.copyProperties(menu, menuDTO);
                    List<ContentDTO.MenuDTO.ButtonDTO> buttonDTOS = dealButtons(buttons, menu);
                    menuDTO.setButtons(buttonDTOS);
                    return menuDTO;
                }).collect(Collectors.toList());
    }

    /**
     * 处理按钮
     *
     * @param buttons 按钮
     * @param menu    菜单
     * @return {@link List}<{@link ContentDTO.MenuDTO.ButtonDTO}>
     */
    private List<ContentDTO.MenuDTO.ButtonDTO> dealButtons(List<SysPermission> buttons, SysPermission menu) {
        return buttons.stream()
                .filter(button -> Objects.equals(button.getParentId(), menu.getId()))
                .map(button -> {
                    ContentDTO.MenuDTO.ButtonDTO buttonDTO = new ContentDTO.MenuDTO.ButtonDTO();
                    BeanUtils.copyProperties(button, buttonDTO);
                    return buttonDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public void register(RegisterVO vo) {

    }
}
