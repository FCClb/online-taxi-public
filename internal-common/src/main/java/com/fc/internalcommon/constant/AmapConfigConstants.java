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
     * 猎鹰轨迹服务 创建服务url
     */
    SERVICE_ADD_URL("https://tsapi.amap.com/v1/track/service/add"),

    /**
     * 猎鹰轨迹服务 创建终端url
     */
    TERMINAL_ADD_URL("https://tsapi.amap.com/v1/track/terminal/add"),

    /**
     * 猎鹰轨迹服务 创建轨迹url
     */
    TRACE_ADD_URL("https://tsapi.amap.com/v1/track/trace/add"),

    /**
     * 猎鹰轨迹服务 轨迹点上传url
     */
    POINT_UPLOAD_URL("https://tsapi.amap.com/v1/track/point/upload"),

    /**
     * 猎鹰轨迹服务 周边搜索终端url
     */
    TERMINAL_AROUNDSEARCH_URL("https://tsapi.amap.com/v1/track/terminal/aroundsearch"),

    /**
     * 猎鹰轨迹服务 查询轨迹信息（轨迹信息包括经纬度点，里程，时间等信息）url
     */
    TERMINAL_TRSEARCH("https://tsapi.amap.com/v1/track/terminal/trsearch"),





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

    /**
     * 地图区划 json key值: street
     */
    STREET("street"),


    ;

    @Getter
    private String value;

    AmapConfigConstants(String value) {
        this.value = value;
    }
}
