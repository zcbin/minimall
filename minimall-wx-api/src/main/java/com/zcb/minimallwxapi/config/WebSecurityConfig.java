package com.zcb.minimallwxapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: zcbin
 * @title: WebSecurityConfig
 * @packageName: com.zcb.minimallwxapi.config
 * @projectName: minimall
 * @description: Spring security 配置类 所有请求都可访问
 * @date: 2020/5/22 15:22
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/wx/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}