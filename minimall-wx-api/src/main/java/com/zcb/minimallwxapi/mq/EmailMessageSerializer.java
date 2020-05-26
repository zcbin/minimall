package com.zcb.minimallwxapi.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallwxapi.dto.EmailMessage;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: zcbin
 * @title: EmailMessageSerializer
 * @packageName: com.zcb.minimallwxapi.util
 * @projectName: minimall
 * @description: EmailMessage 序列化器
 * @date: 2020/5/26 11:07
 */
public class EmailMessageSerializer implements Serializer<EmailMessage> {
    private String encoding = "UTF-8";
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, EmailMessage data) {
        if (data == null) {
            return null;
        }
        return JSON.toJSONBytes(data);

//        return new byte[0];
    }

    @Override
    public void close() {

    }
}
