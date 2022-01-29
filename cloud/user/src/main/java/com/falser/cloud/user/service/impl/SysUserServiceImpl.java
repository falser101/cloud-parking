package com.falser.cloud.user.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.enums.RequestStatus;
import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.user.dao.SysUserDao;
import com.falser.cloud.user.dto.LoginSuccessDTO;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.service.SysUserService;
import com.falser.cloud.user.vo.LoginVO;
import com.falser.cloud.user.vo.RegisterVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户信息(SysUser)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {


}

