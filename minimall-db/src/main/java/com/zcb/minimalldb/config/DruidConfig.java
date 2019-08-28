package com.zcb.minimalldb.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zcbin
 * @title: DruidConfig
 * @projectName minimall
 * @description: druid连接池配置
 * @date 2019/8/27 19:58
 */
@Configuration

public class DruidConfig {
		/**
		 * 参考官网配置
		 * https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
		 * @return
		 */
		@ConfigurationProperties(prefix = "spring.datasource.druid")
		@Bean
		public DataSource dataSource() {
				return  DruidDataSourceBuilder.create().build();
		}
		@Bean
		public ServletRegistrationBean druidStatViewServlet() {
				ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
				Map<String, String> initParams = new HashMap<>();
				//　可配的属性都在 StatViewServlet 和其父类下
				initParams.put("loginUsername", "admin");
				initParams.put("loginPassword", "123456");
				servletRegistrationBean.setInitParameters(initParams);
				return servletRegistrationBean;
		}

		@Bean
		public FilterRegistrationBean druidWebStatFilter() {
				FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
				Map<String, String> initParams = new HashMap<>();
				initParams.put("exclusions", "*.js,*.css,/druid/*");
				filterRegistrationBean.setInitParameters(initParams);
				filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
				return filterRegistrationBean;
		}


}
