package com.zcb.minimalldb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.zcb.minimalldb"})

@MapperScan("com.zcb.minimalldb.dao") //mybatis dao
public class MinimallDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinimallDbApplication.class, args);
    }

}
