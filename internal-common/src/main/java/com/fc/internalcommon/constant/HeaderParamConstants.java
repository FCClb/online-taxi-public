package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * http请求头参数
 */
public enum HeaderParamConstants {

    /**
     * 设备 唯一码
     */
    DEVICE_DEVICE("deviceCode"),

    ;

    @Getter
    private String value;

    private HeaderParamConstants(String value) {
        this.value = value;
    }
}
