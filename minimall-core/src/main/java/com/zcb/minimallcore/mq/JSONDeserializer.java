package com.zcb.minimallcore.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author: zcbin
 * @title: JSONDeserializer
 * @packageName: com.zcb.minimallcore.mq
 * @projectName: minimall
 * @description:
 * @date: 2020/5/26 19:22
 */
public class JSONDeserializer implements Deserializer<JSONObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public JSONObject deserialize(String topic, byte[] data) {
        JSONObject obj = null;
        try {
            obj = JSONObject.parseObject(new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void close() {

    }
}
