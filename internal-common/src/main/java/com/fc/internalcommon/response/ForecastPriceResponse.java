package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 预估价格 响应DTO
 */
@Data
public class ForecastPriceResponse {

    /**
     * 预估价格
     */
    private double price;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 运价类型编码
     */
    private String fareType;

    /**
     * 运价类型版本
     */
    private Integer fareVersion;

}
