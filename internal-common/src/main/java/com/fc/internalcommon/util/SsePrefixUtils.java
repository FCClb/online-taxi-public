package com.fc.internalcommon.util;

/**
 * 消息推送 工具类
 */
public class SsePrefixUtils {

    public static final String sperator = "$";

    public static String generatorSseKey(Long userId, String identity) {
        return userId + sperator + identity;
    }
}
