package com.falser.cloud.user.service;

import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.user.dto.LoginSuccessDTO;
import com.falser.cloud.user.vo.LoginVO;
import com.falser.cloud.user.vo.RegisterVO;

/**
 * 身份验证服务
 *
 * @author falser
 * @date 2022/01/29 15:25:39
 */
public interface AuthService {
    /**
     * 登录
     *
     * @param vo 签证官
     * @return {@link LoginSuccessDTO}
     */
    LoginSuccessDTO login(LoginVO vo);

    /**
     * 获取用户信息
     *
     * @return {@link UserInfoDTO}
     */
    UserInfoDTO getUserInfo();

    /**
     * 注销
     */
    void logout();

    /**
     * 注册
     *
     * @param vo 签证官
     */
    void register(RegisterVO vo);
}
