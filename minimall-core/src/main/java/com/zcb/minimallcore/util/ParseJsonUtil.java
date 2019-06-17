package com.zcb.minimallcore.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
