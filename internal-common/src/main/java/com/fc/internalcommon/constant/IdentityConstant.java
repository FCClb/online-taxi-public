package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * 身份常量
 */
public enum IdentityConstant {

    /**
     * 乘客身份
     */
    PASSENGER_IDENTITY("1"),

    /**
     * 司机身份
     */
    DRIVER_IDENTITY("2"),

    ;

    @Getter
    private String value;

    IdentityConstant(String value) {
        this.value = value;
    }

}
