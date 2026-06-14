package com.fc.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 司机车辆关联关系 实体类
 */
@Data
public class DriverCarBindingRelationship {

    /**
     * 关联id
     */
    private Long id;
    /**
     * 司机id
     */
    private Long driverId;
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 绑定状态（1绑定，2解绑）
     */
    private Integer bindState;
    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;
    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;

}

