package com.zcb.minimallcore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = { "com.zcb.minimalldb","com.zcb.minimallcore"})
//扫描注解元素
@MapperScan("com.zcb.minimalldb.dao") //mybatis dao
//@ComponentScan("com.zcb")
public class MinimallCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinimallCoreApplication.class, args);
    }

}
