package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 第三方地图url
 */
public enum AmapConfigConstants {

    /**
     * 路径规划地址
     */
    DIRECTION_URL("https://restapi.amap.com/v3/direction/driving"),

    /**
     * 路径规划 json key值: status
     */
    STATUS("status"),

    /**
     * 路径规划 json key值: route
     */
    ROUTE("route"),

    /**
     * 路径规划 json key值: path
     */
    PATHS("paths"),

    /**
     * 路径规划 json key值: distance
     */
    DISTANCE("distance"),

    /**
     * 路径规划 json key值: duration
     */
    DURATION("duration")
    ;

    @Getter
    private String value;

    AmapConfigConstants(String url) {
        this.value = url;
    }
}
