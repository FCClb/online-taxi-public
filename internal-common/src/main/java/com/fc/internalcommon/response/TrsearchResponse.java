package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 查询轨迹信息（轨迹信息包括经纬度点，里程，时间等信息）
 * 响应体
 */
@Data
public class TrsearchResponse {

    /**
     * 总行程（米）
     */
    private Long driveMile;

    /**
     * 总用时（分钟）
     */
    private Long driveTime;
}
