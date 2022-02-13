package com.falser.cloud.parking.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * sa牌配置
 *
 * @author 10235
 * @date 2021/11/21
 */
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 注册拦截器
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册Sa-Token的路由拦截器，并排除登录接口或其他可匿名访问的接口地址 (与注解拦截器无关)
        registry
                .addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
                    Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                    // 登录验证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**")
                            .notMatch(Arrays.asList(
                                    "/api/car/identify",
                                    "/doc.html",
                                    "/swagger-ui.html",
                                    "/favicon.ico",
                                    "/**/*.html",
                                    "/**/*.css",
                                    "/**/*.js",
                                    "/swagger-resources/**",
                                    "/swagger-resources/**/**",
                                    "/v2/api-docs",
                                    "/v2/api-docs/**"))
                            .check(StpUtil::checkLogin);

                    // 终止过滤器链
                    SaRouter.match(Arrays.asList(
                                    "/api/car/identify",
                                    "/doc.html",
                                    "/swagger-ui.html",
                                    "/favicon.ico",
                                    "/**/*.html",
                                    "/**/*.css",
                                    "/**/*.js",
                                    "/swagger-resources/**",
                                    "/swagger-resources/**/**",
                                    "/v2/api-docs",
                                    "/v2/api-docs/**"))
                            .stop();

                    handlerMethods.forEach(((requestMappingInfo, handlerMethod) -> {
                        Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                        methods.forEach(requestMethod -> {
                            SaRouter.match(SaHttpMethod.valueOf(requestMethod.name()))
                                    .match(new ArrayList<>(requestMappingInfo.getPatternsCondition().getPatterns()))
                                    .check(() -> {
                                        log.info("请求地址:{}", req.getMethod() + "#" + requestMappingInfo.getPatternsCondition().getPatterns().toArray()[0]);
                                        StpUtil.checkPermission(req.getMethod() + "#" + requestMappingInfo.getPatternsCondition().getPatterns().toArray()[0]);
                                    });
                        });
                    }));

                })).addPathPatterns("/**");
    }
}

