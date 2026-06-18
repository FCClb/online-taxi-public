package com.fc.internalcommon.dto;

import lombok.Data;

/**
 * 价格 实体类
 */
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

    /**
     * 运价类型编码
     */
    private String fareType;

    /**
     * 运价类型版本
     */
    private Integer fareVersion;
}
