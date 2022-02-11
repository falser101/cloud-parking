package com.falser.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author falser
 * @date 2022/01/29 17:42:03
 */
@Data
public class UserInfo implements Serializable {
    private Long serialVersionUID = 2752045484643811986L;

    private String loginName;

    private String username;

    private String email;

    private String mobile;

    private String sex;

    private String loginIp;

    private LocalDateTime loginDate;

    private String avatar;
}
