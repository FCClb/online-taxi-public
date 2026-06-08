package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 预估价格 响应DTO
 */
@Data
public class ForecastPriceResponse {

    /**
     * 预估价格
     */
    private double price;

}
