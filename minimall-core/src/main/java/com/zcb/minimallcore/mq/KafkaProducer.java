package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.vo.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    public void sendMessage(Message message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", message);
        LOGGER.info("producer sent..." + message.toString());
        kafkaTemplate.send(StringUtils.isEmpty(message.getTopic()) ? "topic_null" : message.getTopic(), jsonObject.toString());
    }


}
