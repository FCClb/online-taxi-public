package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 高德猎鹰服务
 * 轨迹点DTO
 */
@Data
public class PointRequest {

    /**
     * 终端id
     */
    private String tid;
    /**
     * 轨迹id
     */
    private String trid;
    /**
     * 坐标
     * 注意类型使用自定义的DTO,防止参数解析出错
     */
    private PointDTO[] points;
}
