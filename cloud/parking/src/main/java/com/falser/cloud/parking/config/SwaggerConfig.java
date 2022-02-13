package com.falser.cloud.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String DEVELOPER_NAME = "接口文档"; // 名称
    public static final String SWAGGER_SCAN_DEVELOPER_PACKAGE = "com.falser.parking.controller";
    public static final String DEVELOPER_VERSION = "1.0.0"; // 版本

    //配置content type
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json", "charset=UTF-8"));

    @Bean
    public Docket createDeveloperApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDeveloperInfo())
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_DEVELOPER_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDeveloperInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("端接口rest风格接口文档")
                .termsOfServiceUrl("falser.top:9000")
                .version(DEVELOPER_VERSION)
                .build();
    }
}
