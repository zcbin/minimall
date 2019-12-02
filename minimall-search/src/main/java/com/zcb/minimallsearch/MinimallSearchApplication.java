package com.zcb.minimallsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zcb.minimallsearch"})
@MapperScan("com.zcb.minimallsearch.dao")
public class MinimallSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinimallSearchApplication.class, args);
    }

}
