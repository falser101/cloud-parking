package com.falser.cloud.user.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;

/**
 * sa牌配置
 *
 * @author 10235
 * @date 2021/11/21
 */
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 注册拦截器
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册Sa-Token的路由拦截器，并排除登录接口或其他可匿名访问的接口地址 (与注解拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor())
                .excludePathPatterns(
//                        List.of("/**")
                        Arrays.asList("/api/auth/login",
                                "/api/auth/register",
                                "/doc.html",
                                "/swagger-ui.html",
                                "/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js",
                                "/swagger-resources/**",
                                "/swagger-resources/**/**",
                                "/v2/api-docs",
                                "/v2/api-docs/**")
                )
                .addPathPatterns("/**");
    }
}

