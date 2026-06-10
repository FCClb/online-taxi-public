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
