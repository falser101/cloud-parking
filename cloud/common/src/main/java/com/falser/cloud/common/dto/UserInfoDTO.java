package com.falser.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息dto
 *
 * @author 10235
 * @date 2021/11/21
 */
@Data
public class UserInfoDTO implements Serializable {
    private Long serialVersionUID = 596167959173178263L;
    /**
     * 的名字
     */
    private String name;
    /**
     * 《阿凡达》
     */
    private String avatar;

    private List<String> roles;

    private List<MenuDTO> menus;
}
