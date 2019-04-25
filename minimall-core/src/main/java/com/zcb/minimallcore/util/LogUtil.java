package com.zcb.minimallcore.util;

import org.apache.log4j.Logger;

/**
 * Log4j工具类
 */
public class LogUtil {


    public static void trace(String msg) {
        getLogger().trace(msg);
    }

    public static void debug(String msg) {
        getLogger().debug(msg);
    }

    public static void info(Object msg) {
        getLogger().info(msg);
    }

    public static void warn(String msg) {
        getLogger().warn(msg);
    }

    public static void error(String msg) {
        getLogger().error(msg);
    }

    public static void error(String msg, Throwable t) {
        getLogger().error(msg, t);
    }

    private static Logger getLogger() {
        return Logger.getLogger(findCaller().getClassName());
    }

    private static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;

        // 日志类名称

        String logClassName = LogUtil.class.getName();
        // 循环遍历到日志类标识

        int i = 0;
        for (int len = callStack.length; i < len; i++) {
            if (logClassName.equals(callStack[i].getClassName())) {
                break;
            }
        }
        caller = callStack[i + 3];
        return caller;
    }
}

