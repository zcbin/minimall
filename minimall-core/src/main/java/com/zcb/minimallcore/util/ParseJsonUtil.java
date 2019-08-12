package com.zcb.minimallcore.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * @author zcbin
 * @title: ParseJsonUtil
 * @projectName minimall
 * @description: TODO
 * @date 2019/6/17 21:23
 */
public class ParseJsonUtil {
    public static Integer parseInteger(String body, String field) {
        JSONObject json = JSONObject.parseObject(body);
        return json.getInteger(field);
    }
    public static String parseString(String body, String field) {
        JSONObject json = JSONObject.parseObject(body);
        return json.getString(field);
    }
    public static List<Integer> parseListInteger(String body, String field) {
        JSONObject json = JSONObject.parseObject(body);
        return json.getObject(field, new TypeReference<List<Integer>>(){});
    }
    public static List<String> parseStringList(String body, String field) {
        JSONObject json = JSONObject.parseObject(body);
        return json.getObject(field, new TypeReference<List<String>>(){});
    }
}
