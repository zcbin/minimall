package com.zcb.minimalladminapi;

import com.zcb.minimalladminapi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class MinimallAdminApiApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
        userService.save();
    }

}
