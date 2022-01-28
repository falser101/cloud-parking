package com.falser.cloud.common.enums;

import lombok.Getter;

/**
 * @author falser
 * @version 1.0.0
 * @description 状态
 * @date 2021/05/22
 * @see Enum
 * @since 1.0.0
 */
@Getter
public enum RequestStatus {

    /**
     * 操作成功！
     */
    SUCCESS(200, "操作成功！"),

    /**
     * 操作异常！
     */
    ERROR(500, "操作异常！"),

    /**
     * 退出成功！
     */
    LOGOUT(200, "退出成功！"),

    /**
     * 请先登录！
     */
    UNAUTHORIZED(401, "请先登录！"),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(403, "权限不足！"),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(404, "请求不存在！"),

    /**
     * 请求方式不支持！
     */
    HTTP_BAD_METHOD(405, "请求方式不支持！"),

    /**
     * 请求异常！
     */
    BAD_REQUEST(400, "请求异常！"),

    /**
     * 参数不匹配！
     */
    PARAM_NOT_MATCH(400, "参数不匹配！"),

    /**
     * 参数不能为空！
     */
    PARAM_NOT_NULL(400, "参数不能为空！"),

    /**
     * 当前用户已被锁定，请联系管理员解锁！
     */
    USER_DISABLED(403, "当前用户已被锁定，请联系管理员解锁！"),

    /**
     * 用户名或密码错误！
     */
    USERNAME_PASSWORD_ERROR(601, "用户名或密码错误！"),

    /**
     * 用户没有注册错误
     */
    USER_NOT_REGISTER_ERROR(602, "该手机号未注册"),

    /**
     * token 已过期，请重新登录！
     */
    TOKEN_EXPIRED(5002, "token 已过期，请重新登录！"),

    /**
     * 访问限制
     */
    ACCESS_LIMIT(500, "重复请求"),

    /**
     * token 解析失败，请尝试重新登录！
     */
    TOKEN_PARSE_ERROR(5002, "token 解析失败，请尝试重新登录！"),

    /**
     * 当前用户已在别处登录，请尝试更改密码或重新登录！
     */
    TOKEN_OUT_OF_CTRL(5003, "当前用户已在别处登录，请尝试更改密码或重新登录！"),

    /**
     * 无法手动踢出自己，请尝试退出登录操作！
     */
    KICKOUT_SELF(5004, "无法手动踢出自己，请尝试退出登录操作！"),

    /**
     * 验证码不一致
     */
    CHECK_CODE_ERROR(5005, "验证码不一致！"),

    /**
     * 用户存在异常
     */
    USER_EXIST_EXCEPTION(5007, "用户名存在"),

    /**
     * 手机存在异常
     */
    PHONE_EXIST_EXCEPTION(5008, "手机号已被注册"),

    /**
     * 数据库中没有这个文章
     */
    BLOG_NOT_EXIST(5009, "文章不存在"),

    /**
     * 数据库中没有这个文章
     */
    BLOG_NO_AUTHORITY(5010, "没有该文章的操作权限"),

    /**
     * 文件为空
     */
    FILE_NULL(5006, "文件为空");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回信息
     */
    private final String msg;

    RequestStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RequestStatus fromCode(Integer code) {
        RequestStatus[] statuses = RequestStatus.values();
        for (RequestStatus status : statuses) {
            if (status.getCode()
                    .equals(code)) {
                return status;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" Status:{code=%s, msg=%s} ", getCode(), getMsg());
    }
}
