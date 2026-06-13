package com.fc.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * token 返回解析值
 */
@Data
@AllArgsConstructor
public class TokenResult {

    private String phone;

    private String identity;
}
