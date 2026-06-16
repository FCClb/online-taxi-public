package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 高德猎鹰服务
 * 轨迹点 坐标 DTO
 */
@Data
public class PointDTO {

    /**
     * 上传坐标
     */
    private String location;

    /**
     * 上传时间
     */
    private Long locatetime;
}
