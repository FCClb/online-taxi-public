package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 预估价格 请求DTO
 */
@Data
public class ForecastPriceDTO {

    /**
     * 出发地点经度
     */
    private String depLongitude;

    /**
     * 出发地点纬度
     */
    private String depLatitude;

    /**
     * 目标地点经度
     */
    private String destLongitude;

    /**
     * 目标地点纬度
     */
    private String destLatitude;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

}
