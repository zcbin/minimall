package com.zcb.minimallwxapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zcb.minimalldb", "com.zcb.minimallcore", "com.zcb.minimallwxapi"})
@MapperScan("com.zcb.minimalldb.dao") //mybatis dao
@EnableTransactionManagement //开启事务管理
public class MinimallWxApiApplication {
    //
    public static void main(String[] args) {
        SpringApplication.run(MinimallWxApiApplication.class, args);
    }

}
