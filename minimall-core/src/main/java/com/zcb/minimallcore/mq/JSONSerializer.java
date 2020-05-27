package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author: zcbin
 * @title: JSONSerializer
 * @packageName: com.zcb.minimallcore.mq
 * @projectName: minimall
 * @description: JSON序列化
 * @date: 2020/5/26 19:16
 */
public class JSONSerializer implements Serializer<JSONObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, JSONObject data) {
        try {
            return data.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        //return new byte[0];
    }

    @Override
    public void close() {

    }
}
