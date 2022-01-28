package com.falser.cloud.gateway.service;

import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.common.web.ApiResponse;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author ruoyi
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
     ApiResponse<String> createCapcha() throws IOException;

    /**
     * 校验验证码
     */
    void checkCapcha(String key, String value) throws BaseException;
}

