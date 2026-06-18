package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 订单常量
 */
public enum OrderConstants {

    /**
     * 订单开始
     */
    ORDER_START(1),

    /**
     * 司机接单
     */
    DRIVER_RECEIVE_ORDER(2),

    /**
     * 司机去接乘客
     */
    DRIVER_TO_PICK_UP_PASSENGER(3),

    /**
     * 司机到达乘客起点
     */
    DRIVER_ARRIVED_DEPARTURE(4),

    /**
     * 司机开始行程
     */
    PICK_UP_PASSENGER(5),

    /**
     * 到达目的地，行程结束，未支付
     */
    PASSENGER_GET_OFF(6),

    /**
     * 发起收款
     */
    TO_START_PAY(7),

    /**
     * 支付完成
     */
    SUCCESS_PAY(8),

    /**
     * 订单取消
     */
    ORDER_CANCEL(9),

    ;

    @Getter
    private int code;

    OrderConstants(int code) {
        this.code = code;
    }
}
