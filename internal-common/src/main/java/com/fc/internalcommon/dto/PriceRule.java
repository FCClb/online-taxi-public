package com.fc.internalcommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 车型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 每公里价钱
     */
    private Double unitPricePerMile;

    /**
     * 每分钟价钱
     */
    private Double unitPricePerMinute;

}
