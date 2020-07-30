package com.zcb.minimallwxapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: zcbin
 * @title: Swagger2Configuration
 * @packageName: com.zcb.minimallwxapi.config
 * @projectName: minimall
 * @description: Swagger配置
 * @date: 2020/6/1 10:41
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    //api 接口扫描路径
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.zcb.minimallwxapi.controller";

    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("minimall") //设置文档的标题
                .description("微商城后端 API 接口文档") // 设置文档的描述
                .termsOfServiceUrl("http://127.0.0.1:8081/wx") //License information
                .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                .build();
    }


}
