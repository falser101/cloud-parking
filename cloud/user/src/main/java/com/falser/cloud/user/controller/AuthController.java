package com.falser.cloud.user.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.dto.LoginSuccessDTO;
import com.falser.cloud.user.service.AuthService;
import com.falser.cloud.user.service.SysUserService;
import com.falser.cloud.user.vo.LoginVO;
import com.falser.cloud.user.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 身份验证控制器
 *
 * @author 10235
 * @date 2021/12/12
 */
@Api(tags = "认证")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public ApiResponse<LoginSuccessDTO> login(@Valid @RequestBody LoginVO vo) {
        return ApiResponse.ofSuccess(authService.login(vo));
    }

    @SaCheckLogin
    @GetMapping("userinfo")
    @ApiOperation("获取登录用户信息")
    public ApiResponse<UserInfoDTO> getUserInfo() {
        return ApiResponse.ofSuccess(authService.getUserInfo());
    }

    @SaCheckLogin
    @PostMapping("logout")
    @ApiOperation("登出")
    public ApiResponse<String> logout() {
        authService.logout();
        return ApiResponse.ofSuccess();
    }

    @PostMapping("register")
    @ApiOperation("注册")
    public ApiResponse<String> register(@Valid @RequestBody RegisterVO vo) {
        if (!Objects.equals(vo.getPassword(), vo.getRePassword())) {
            throw new BaseException("两次密码不一致");
        }
        authService.register(vo);
        return ApiResponse.ofSuccess();
    }
}
