package com.falser.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.user.dto.LoginSuccessDTO;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.vo.LoginVO;
import com.falser.cloud.user.vo.RegisterVO;

/**
 * 用户信息(SysUser)表服务接口
 *
 * @author falser
 * @since 2022-01-29 00:51:06
 */
public interface SysUserService extends IService<SysUser> {
}

