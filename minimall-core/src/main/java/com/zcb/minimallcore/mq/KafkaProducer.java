package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: zcbin
 * @title: KafkaProducer
 * @packageName: com.zcb.minimallcore.mq
 * @projectName: minimall
 * @description: kafka生产者
 * @date: 2020/5/20 16:04
 */
@Component
public class KafkaProducer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(String topic, JSONObject jsonObject) {
        if (topic == null) {
            LOGGER.info("topic为空,发送数据:" + jsonObject.toString());
            return;
        }
        kafkaTemplate.send(topic, jsonObject);
    }


}
