package com.zcb.minimallcore;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.mq.KafkaProducer;
import com.zcb.minimallcore.vo.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinimallCoreApplicationTests {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Test
    public void contextLoads() {
        Message message = new Message();
        message.setId(1L);
        message.setMessage("kafka test message");
        message.setLocalDateTime(LocalDateTime.now());

        kafkaProducer.sendMessage(message);
    }

}
