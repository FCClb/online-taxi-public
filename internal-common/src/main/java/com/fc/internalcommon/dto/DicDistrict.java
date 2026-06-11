package com.fc.internalcommon.dto;

import lombok.Data;

/**
 * 地区字典dto
 */
@Data
public class DicDistrict {

    /**
     * 地区编码
     */
    private String addressCode;

    /**
     * 地区名称
     */
    private String addressName;

    /**
     * 父地区名称
     */
    private String parentAddressCode;

    /**
     * 级别
     */
    private Integer level;
}
