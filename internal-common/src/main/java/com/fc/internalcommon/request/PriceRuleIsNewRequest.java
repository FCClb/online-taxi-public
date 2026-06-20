package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 判断当前定价规则是否最新 请求体
 */
@Data
public class PriceRuleIsNewRequest {

    /**
     * 运价类型编码
     */
    private String fareType;

    /**
     * 运价类型版本
     */
    private Integer FareVersion;
}
