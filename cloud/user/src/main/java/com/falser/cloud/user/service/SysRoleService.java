package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.user.dto.RoleDetailDTO;
import com.falser.cloud.user.entity.SysRole;
import com.falser.cloud.user.vo.RoleAddVO;
import com.falser.cloud.user.vo.RoleDetailVO;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过id获取角色的细节
     *
     * @param id id
     * @return {@link RoleDetailDTO}
     */
    RoleDetailDTO getRoleDetailById(Long id);

    /**
     * 更新的作用
     *
     * @param vo 签证官
     */
    void updateRole(RoleDetailVO vo);

    /**
     * 添加角色
     *
     * @param vo 签证官
     */
    void addRole(RoleAddVO vo);

    void deleteRoleById(Long id);
}

