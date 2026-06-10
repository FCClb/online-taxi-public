package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 第三方地图url
 */
public enum AmapConfigConstants {

    /**
     * 路径规划url
     */
    DIRECTION_URL("https://restapi.amap.com/v3/direction/driving"),

    /**
     * 行政区域查询url
     */
    DISTRICT_URL("https://restapi.amap.com/v3/config/district"),

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
    DURATION("duration"),

    /**
     * 地图区划 json key值: districts
     */
    DISTRICTS("districts"),

    /**
     * 地图区划 json key值: adcode
     */
    ADCODE("adcode"),

    /**
     * 地图区划 json key值: name
     */
    NAME("name"),

    /**
     * 地图区划 json key值: level
     */
    LEVEL("level"),

    ;

    @Getter
    private String value;

    AmapConfigConstants(String value) {
        this.value = value;
    }
}
