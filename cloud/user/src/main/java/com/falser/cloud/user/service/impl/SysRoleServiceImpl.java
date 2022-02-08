package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.user.dao.SysRoleDao;
import com.falser.cloud.user.dto.RoleDetailDTO;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.entity.SysRolePermission;
import com.falser.cloud.user.service.SysRolePermissionService;
import com.falser.cloud.user.service.SysRoleService;
import com.falser.cloud.user.vo.RoleAddVO;
import com.falser.cloud.user.vo.RoleDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    private final SysRolePermissionService sysRolePermissionService;

    public SysRoleServiceImpl(SysRolePermissionService sysRolePermissionService) {
        this.sysRolePermissionService = sysRolePermissionService;
    }
    @Override
    public RoleDetailDTO getRoleDetailById(Long id) {
        SysRole byId = getById(id);
        List<Long> menuList = sysRolePermissionService.getPermissionIdsByRoleIdAndType(id, PermissionTypeEnum.MENU);

        RoleDetailDTO roleDetailDTO = new RoleDetailDTO();
        BeanUtils.copyProperties(byId, roleDetailDTO);
        roleDetailDTO.setPermList(menuList);
        return roleDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleDetailVO vo) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(vo, entity, "interfaceList", "menuList");
        updateById(entity);
        sysRolePermissionService.remove(new LambdaUpdateWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, vo.getId()));
        List<SysRolePermission> sysRolePermissions = getRolePermissions(vo.getInterfaceList(), vo.getMenuList(), vo.getId());
        sysRolePermissionService.saveBatch(sysRolePermissions);
    }

    private List<SysRolePermission> getRolePermissions(List<Long> interfaceList, List<Long> menuList, Long vo2) {
        interfaceList.addAll(menuList);
        return interfaceList.stream().map(id -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(vo2);
            sysRolePermission.setPermissionId(id);
            return sysRolePermission;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleAddVO vo) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(vo, entity, "interfaceList", "menuList");
        save(entity);
        List<SysRolePermission> sysRolePermissions = getRolePermissions(List.of(), vo.getMenuList(), entity.getId());
        sysRolePermissionService.saveBatch(sysRolePermissions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(Long id) {
        removeById(id);
        sysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>()
                .eq(SysRolePermission::getRoleId, id));
    }
}

