package com.falser.cloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface RedisKeyEnum {

    @Getter
    @AllArgsConstructor
    enum LoginKeyEnum implements RedisKeyEnum {
        USER_ID_TOKEN_REF_KEY("satoken:login:userId:", "用户id和用户登录token的关联key"),
        LOGIN_PERMISSIONS_KEY("satoken:login:permissions:", ""),
        LOGIN_ROLES_KEY("satoken:login:roles:", ""),
        ;

        private final String cacheName;
        private final String msg;

        @Override
        public String getCacheName() {
            return cacheName;
        }
    }

    String getCacheName();
}
