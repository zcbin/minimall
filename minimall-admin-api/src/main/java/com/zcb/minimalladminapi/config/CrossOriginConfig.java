package com.zcb.minimalladminapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zcbin
 * @title: CrossOriginConfig
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/9 14:35
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/admin/**")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

}
