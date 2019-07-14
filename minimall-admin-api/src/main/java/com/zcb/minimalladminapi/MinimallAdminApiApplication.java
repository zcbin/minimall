package com.zcb.minimalladminapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zcb.minimalldb", "com.zcb.minimallcore", "com.zcb.minimalladminapi"})
//@ComponentScan("com.zcb") //扫描注解元素
@MapperScan("com.zcb.minimalldb.dao") //mybatis dao
@EnableTransactionManagement //开启事务管理
public class MinimallAdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinimallAdminApiApplication.class, args);
    }

}
