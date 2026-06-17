package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 高德猎鹰服务
 * api端-轨迹点DTO
 */
@Data
public class ApiDriverPointRequest {

    /**
     * 车辆id
     */
    private Long carId;

    /**
     * 轨迹点数组
     */
    private PointDTO[] points;

}
