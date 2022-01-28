package com.falser.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单dto
 *
 * @author 10235
 * @date 2021/11/20
 */
@Data
public class MenuDTO implements Serializable {

    private Long serialVersionUID = 596167959173178262L;

    private Long id;

    private String perms;

    private String icon;

    private String permissionName;
}
