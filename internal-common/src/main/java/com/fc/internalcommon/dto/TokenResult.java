package com.fc.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * token 返回解析值
 */
@Data
@AllArgsConstructor
public class TokenResult {

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份标识
     */
    private String identity;
}
