package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 根据carId查询可派单的司机信息 响应体
 */
@Data
public class OrderDriverResponse {

    /**
     * 司机Id
     */
    private Long driverId;

    /**
     * 司机手机号码
     */
    private String driverPhone;

    /**
     * 车辆Id
     */
    private Long carId;
}
