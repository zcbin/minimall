package com.zcb.minimalldb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zcbin
 * @title: DruidConfig
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/27 19:58
 */
@Configuration
//@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})

public class DruidConfig {
		// 将所有前缀为spring.datasource下的配置项都加载到DataSource中
		@Autowired
		private DruidProties druidProties;
		//@ConfigurationProperties(prefix = "spring.datasource")
		//@Bean
		public DataSource dataSource() {
				System.out.println("----"+druidProties.getUrl());
				return new DruidDataSource();
		}
		private static final String DB_PREFIX = "spring.datasource";
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
		//解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
	//	@Component
		//@ConfigurationProperties(prefix = DB_PREFIX)
//		class IDataSourceProperties {
//
//
//				//@Bean     //声明其为Bean实例
//				//@Primary  //在同样的DataSource中，首先使用被标注的DataSource
//				public DataSource dataSource() {
//						System.out.println("url="+url);
//						System.out.println("maxActive"+maxActive);
//						DruidDataSource datasource = new DruidDataSource();
//						datasource.setUrl(url);
//						datasource.setUsername(username);
//						datasource.setPassword(password);
//						datasource.setDriverClassName(driverClassName);
//
//						//configuration
//						datasource.setInitialSize(initialSize);
//						datasource.setMinIdle(minIdle);
//						datasource.setMaxActive(maxActive);
//						datasource.setMaxWait(maxWait);
//						datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//						datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//						datasource.setValidationQuery(validationQuery);
//						datasource.setTestWhileIdle(testWhileIdle);
//						datasource.setTestOnBorrow(testOnBorrow);
//						datasource.setTestOnReturn(testOnReturn);
//						datasource.setPoolPreparedStatements(poolPreparedStatements);
//						datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//						try {
//								datasource.setFilters(filters);
//						} catch (SQLException e) {
//								System.err.println("druid configuration initialization filter: " + e);
//						}
//						datasource.setConnectionProperties(connectionProperties);
//						return datasource;
//				}
//
//
//		}

}
