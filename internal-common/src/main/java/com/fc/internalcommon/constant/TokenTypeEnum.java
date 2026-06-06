package com.fc.internalcommon.constant;

import lombok.Getter;

/**
 * token类型常量
 */
public enum TokenTypeEnum {

    /**
     * accessToken类型
     */
    ACCESS_TOKEN_TYPE("accessToken"),

    /**
     * refreshToken类型
     */
    REFRESH_TOKEN_TYPE("refreshToken"),
    ;

    @Getter
    private String tokenType;

    TokenTypeEnum(String tokenType) {
        this.tokenType = tokenType;
    }
}
