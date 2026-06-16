package com.fc.internalcommon.dto;

import lombok.Data;

/**
 * 高德地图-猎鹰轨迹服务-轨迹响应
 */
@Data
public class TraceResponse {

    //轨迹id
    private String trid;

    //轨迹名称
    private String trname;
}
