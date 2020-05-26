package com.zcb.minimallcore.util;

/**
 * @author: zcbin
 * @title: ArrayUtil
 * @packageName: com.zcb.minimallcore.util
 * @projectName: minimall
 * @description:
 * @date: 2020/5/25 16:04
 */
public class ArrayUtil {
    public static boolean isNotEmpty(String... cc) {
        if (cc != null && cc.length > 0) {
            return true;
        }
        return false;
    }
}
