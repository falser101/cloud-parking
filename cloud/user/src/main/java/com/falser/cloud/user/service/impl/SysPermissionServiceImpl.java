package com.falser.cloud.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.dto.ContentDTO;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.user.dao.SysPermissionDao;
import com.falser.cloud.user.dto.MenuTree;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.service.SysPermissionService;
import com.falser.cloud.user.vo.PermissionAddVO;
import com.falser.cloud.user.vo.PermissionUpdateVO;
import com.falser.cloud.user.vo.PermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysPermission)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

    @Override
    public IPage<MenuTree> getPageByVo(PermissionVO vo) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<SysPermission>()
                .like(Objects.nonNull(vo.getRouterName()), SysPermission::getRouterName, vo.getRouterName())
                .like(Objects.nonNull(vo.getPerms()), SysPermission::getPerms, vo.getPerms())
                .eq(SysPermission::getParentId, 0L);
        Page<SysPermission> page = page(new Page<>(vo.getCurrent(), vo.getSize()), queryWrapper);

        List<SysPermission> records = page.getRecords();
        List<MenuTree> collect = getContents(records);
        Page<MenuTree> menuTreePage = new Page<>();
        BeanUtils.copyProperties(page, menuTreePage);
        menuTreePage.setRecords(collect);
        return menuTreePage;
    }

    @Override
    public void addPermission(PermissionAddVO vo) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(vo, sysPermission);
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        sysPermission.setCreateBy(userinfo.getUserInfo().getUsername());
        sysPermission.setPermissionType(vo.getPermissionType());
        save(sysPermission);
    }

    @Override
    public void updatePermission(PermissionUpdateVO vo) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(vo, sysPermission);
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        sysPermission.setUpdateBy(userinfo.getUserInfo().getUsername());
        sysPermission.setPermissionType(vo.getPermissionType());
        baseMapper.updateById(sysPermission);
    }

    @Override
    public List<ContentDTO.MenuDTO> getLevel1Permission(PermissionTypeEnum permissionTypeEnum) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<SysPermission>()
                .eq(SysPermission::getPermissionType, permissionTypeEnum.name());
        List<SysPermission> list = list(queryWrapper);
        list = Optional.ofNullable(list).orElse(new ArrayList<>());
        return list.stream().map(permission -> {
            ContentDTO.MenuDTO menuDTO = new ContentDTO.MenuDTO();
            BeanUtils.copyProperties(permission, menuDTO);
            return menuDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 处理目录
     *
     * @param records re
     * @return {@link List}<{@link MenuTree}>
     */
    private List<MenuTree> getContents(List<SysPermission> records) {
        return records.stream().map(sysPermission -> {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(sysPermission, menuTree);
            List<SysPermission> list = list(new LambdaQueryWrapper<SysPermission>()
                    .eq(SysPermission::getParentId, sysPermission.getId()));

            if (!CollectionUtils.isEmpty(list)) {
                getMenus(menuTree, list);
            }
            return menuTree;
        }).collect(Collectors.toList());
    }

    /**
     * 处理菜单
     *
     * @param menuTree menu
     * @param list l
     */
    private void getMenus(MenuTree menuTree, List<SysPermission> list) {
        List<MenuTree> collect1 = list.stream().map(sysPermission1 -> {
            MenuTree menuTree1 = new MenuTree();
            BeanUtils.copyProperties(sysPermission1, menuTree1);
            List<SysPermission> list1 = list(new LambdaQueryWrapper<SysPermission>()
                    .eq(SysPermission::getParentId, sysPermission1.getId()));
            if (!CollectionUtils.isEmpty(list1)) {
                getButtons(menuTree1, list1);
            }
            return menuTree1;
        }).collect(Collectors.toList());
        menuTree.setChildren(collect1);
    }

    /**
     * 处理按钮
     *
     * @param menuTree menu
     * @param list list
     */
    private void getButtons(MenuTree menuTree, List<SysPermission> list) {
        List<MenuTree> collect = list.stream().map(sysPermission -> {
            MenuTree menuTree1 = new MenuTree();
            BeanUtils.copyProperties(sysPermission, menuTree1);
            return menuTree1;
        }).collect(Collectors.toList());
        menuTree.setChildren(collect);
    }
}

