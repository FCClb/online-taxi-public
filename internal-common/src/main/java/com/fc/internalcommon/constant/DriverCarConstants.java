package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 司机车辆关系 常量
 */
public enum DriverCarConstants {

    /**
     * 司机车辆关系状态：绑定状态
     */
    DRIVER_CAR_BIND(1),

    /**
     * 司机车辆关系状态：解绑状态
     */
    DRIVER_CAR_UNBIND(2),

    ;


    @Getter
    private Integer state;

    DriverCarConstants(Integer state) {
        this.state = state;
    }
}
