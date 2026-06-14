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

    /**
     * 司机账号状态：有效
     */
    DRIVER_STATE_VALID(0),

    /**
     * 司机账号状态：失效
     */
    DRIVER_STATE_INVALID(1),

    /**
     * 根据手机号查询司机：司机存在
     */
    DRIVER_EXISTS(1),


    /**
     * 根据手机号查询司机：司机不存在
     */
    DRIVER_NOT_EXISTS(0),

    ;


    @Getter
    private Integer state;

    DriverCarConstants(Integer state) {
        this.state = state;
    }
}
