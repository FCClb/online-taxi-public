package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 验证码常量
 */
public enum CommonStatusEnum {

    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),

    /**
     * token错误提示：1100-1199
     */
    TOKEN_ERROR(1199, "token错误"),

    /**
     * 用户错误提示：1200-1299
     */
    USER_NOT_EXISTS(1299, "当前用户不存在"),

    /**
     * 计价规则错误提示：1300-1399
     */
    PRICE_RULE_EMPTY(1399, "计价规则不存在"),

    /**
     * 地图信息错误提示：1400-1499
     */
    MAP_DISTRICT_ERROR(1499, "请求地图错误"),

    /**
     * 司机车辆关系 错误提示：1500-1599
     */
    DRIVER_CAR_BIND_NOT_EXISTS(1500, "司机和车辆绑定关系不存在"),

    DRIVER_NOT_EXISTS(1501,"司机不存在"),

    DRIVER_CAR_BIND_EXISTS(1502, "司机和车辆已经绑定，请勿重复绑定"),

    DRIVER_BIND_EXISTS(1503, "司机已经绑定，请勿重复绑定"),

    CAR_BIND_EXISTS(1504, "车辆已经绑定，请勿重复绑定"),

    CAR_NOT_EXISTS(1505, "车辆不存在"),



    /**
     * 猎鹰服务
     * url拼接错误提示：2000-2099
     */
    URL_ERROR(2099, "url拼接错误"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 失败
     */
    FAIL(0, "fail"),

    ;

    @Getter
    private int code;

    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
