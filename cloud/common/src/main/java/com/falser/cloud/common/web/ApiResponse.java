package com.falser.cloud.common.web;

import com.falser.cloud.common.enums.RequestStatus;
import com.falser.cloud.common.exception.BaseException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * api的反应
 *
 * @author falser
 * @version 1.0.0
 * @description 通用的 API 接口封装
 * @date 2021/03/29
 * @since 1.0.0
 */
@Data
@Setter
@Getter
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 8993485788201922830L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 无参构造函数
     */
    private ApiResponse() {

    }

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     */
    private ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    返回数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> of(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    /**
     * 构造一个成功且不带数据的API返回
     *
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * 构造一个成功且带数据的API返回
     *
     * @param data 返回数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ofSuccess(T data) {
        return ofStatus(RequestStatus.SUCCESS, data);
    }

    /**
     * 构造一个成功且自定义消息的API返回
     *
     * @param message 返回内容
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ofMessage(String message) {
        return of(RequestStatus.SUCCESS.getCode(), message, null);
    }

    /**
     * 构造一个有状态的API返回
     *
     * @param status 状态 {@link RequestStatus}
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ofStatus(RequestStatus status) {
        return ofStatus(status, null);
    }

    /**
     * 构造一个有状态且带数据的API返回
     *
     * @param status 状态 {@link RequestStatus}
     * @param data   返回数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ofStatus(RequestStatus status, T data) {
        return of(status.getCode(), status.getMsg(), data);
    }

    /**
     * 构造一个异常的API返回
     *
     * @param t   异常
     * @param <T> {@link BaseException} 的子类
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse<String> ofException(T t) {
        return of(500, t.getMessage(), t.getDefaultMessage());
    }

    public static <T> ApiResponse<T> ofException(String message) {
        return of(500, message, null);
    }
}
