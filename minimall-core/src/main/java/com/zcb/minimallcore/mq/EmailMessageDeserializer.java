package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSON;
import com.zcb.minimallcore.vo.EmailMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * @author: zcbin
 * @title: EmailMessageDeserializer
 * @packageName: com.zcb.minimallwxapi.util
 * @projectName: minimall
 * @description: EmailMessage 反序列化器
 * @date: 2020/5/26 17:46
 */
public class EmailMessageDeserializer implements Deserializer<EmailMessage> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public EmailMessage deserialize(String topic, byte[] data) {
        return JSON.parseObject(data, EmailMessage.class);
    }

    @Override
    public void close() {

    }
}
