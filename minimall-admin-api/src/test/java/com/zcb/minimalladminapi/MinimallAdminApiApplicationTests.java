package com.zcb.minimalladminapi;

import com.zcb.minimalladminapi.service.UserService;
import com.zcb.minimalladminapi.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
@ActiveProfiles({"db", "core", "admin"})
public class MinimallAdminApiApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Test
   public void redisTest() {
        //RedisUtil redisUtil = new RedisUtil();
        redisUtil.set("key", "helloaa");
   }

}
