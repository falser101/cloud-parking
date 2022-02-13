package com.falser.cloud.parking.handler;

import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.common.web.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author falser
 * @version 1.0.0
 * @description 全局异常处理程序
 * @date 2021/03/29
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ApiResponse<String> baseException(BaseException e) {
        return ApiResponse.ofException(e.getDefaultMessage());
    }

    /**
     * 处理异常
     *
     * @param e e
     * @return {@link ApiResponse<String>}
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.ofException(e.getMessage());
    }

    /**
     * 处理异常
     *
     * @param e e
     * @return {@link ApiResponse<String>}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append("[").append(error.getDefaultMessage()).append("] ");
        }
        log.error(sb.toString(), e);
        return ApiResponse.ofException(sb.toString());
    }
}
