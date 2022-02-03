package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.common.dto.ContentDTO;
import com.falser.cloud.common.enums.PermissionTypeEnum;
import com.falser.cloud.user.dto.MenuTree;
import com.falser.cloud.user.entity.SysPermission;
import com.falser.cloud.user.vo.PermissionAddVO;
import com.falser.cloud.user.vo.PermissionUpdateVO;
import com.falser.cloud.user.vo.PermissionVO;

import java.util.List;

/**
 * 菜单权限表(SysPermission)表服务接口
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**
     * 分页查询
     *
     * @param vo 签证官
     * @return {@link Page}<{@link SysPermission}>
     */
    IPage<MenuTree> getPageByVo(PermissionVO vo);

    /**
     * 添加权限
     *
     * @param vo 签证官
     */
    void addPermission(PermissionAddVO vo);

    /**
     * 更新权限
     *
     * @param vo 签证官
     */
    void updatePermission(PermissionUpdateVO vo);

    /**
     * 一级菜单或界面
     *
     * @param permissionTypeEnum 许可类型的枚举
     * @return {@link List}<{@link ContentDTO.MenuDTO}>
     */
    List<ContentDTO.MenuDTO> getLevel1Permission(PermissionTypeEnum permissionTypeEnum);
}

