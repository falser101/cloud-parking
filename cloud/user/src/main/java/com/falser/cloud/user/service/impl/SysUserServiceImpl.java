package com.falser.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.enums.RequestStatus;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.user.dao.SysUserDao;
import com.falser.cloud.user.entity.SysUser;
import com.falser.cloud.user.service.SysUserService;
import com.falser.cloud.user.vo.SysUserInsertOrUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户信息(SysUser)表服务实现类
 *
 * @author falser
 * @since 2022-01-29 00:53:09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSystemUser(SysUserInsertOrUpdateVO vo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setSex(vo.getSex());
        sysUser.setStatus(vo.getStatus());
        // 修改
        if (Objects.nonNull(sysUser.getId())) {
            updateById(sysUser);
        } else {
            checkAlreadyExist(sysUser);
            sysUser.setPassword(passwordEncoder.encode("123456"));
            save(sysUser);
        }
    }

    /**
     * 检查已经存在
     *
     * @param sysUser 系统用户
     */
    private void checkAlreadyExist(SysUser sysUser) {
        SysUser user = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getMobile, sysUser.getMobile()));
        if (Objects.nonNull(user)) {
            throw new BaseException(RequestStatus.PHONE_EXIST_EXCEPTION);
        }

        SysUser user1 = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getLoginName, sysUser.getLoginName()));
        if (Objects.nonNull(user1)) {
            throw new BaseException(RequestStatus.USER_EXIST_EXCEPTION);
        }

    }
}

