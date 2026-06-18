package com.fc.internalcommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单 乘客请求
 */
@Data
public class OrderRequest {
    /**
     * 下单行政区域
     */
    private String address;

    /**
     * 出发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    /**
     * 出发地点
     */
    private String departure;

    /**
     * 出发经度
     */
    private String depLongitude;

    /**
     * 出发纬度
     */
    private String depLatitude;

    /**
     * 目的地地址
     */
    private String destination;

    /**
     * 目的地经度
     */
    private String destLongitude;

    /**
     * 目的地纬度
     */
    private String destLatitude;

    /**
     * 坐标加密标识(1：GCJ-02 测绘局标准；2：WGS84 GPS标准；3：JBD-09 百度标准；4：CGCS2000 北斗标准；0：其他)
     */
    private String encrypt;

    /**
     * 运价类型编码
     */
    private String fareType;
}
