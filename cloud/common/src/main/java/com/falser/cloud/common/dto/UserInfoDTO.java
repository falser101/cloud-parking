package com.falser.cloud.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息dto
 *
 * @author 10235
 * @date 2021/11/21
 */
@Data
public class UserInfoDTO implements Serializable {
    @JsonIgnore
    private Long serialVersionUID = 596167959173178263L;

    private UserInfo userInfo;

    private List<String> roles;

    private List<ContentDTO> contents;
}
