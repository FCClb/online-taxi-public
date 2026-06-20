package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 高德地图-猎鹰轨迹服务-终端响应
 */
@Data
public class TerminalResponse {

    /**
     * 终端id
     */
    private String tid;

    /**
     * 车辆id
     */
    private Long carId;

    /**
     * 终端经度
     */
    private String longitude;

    /**
     * 终端纬度
     */
    private String latitude;

}
