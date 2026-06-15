package com.fc.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (DriverUserWorkStatus)实体类
 */
@Data
public class DriverUserWorkStatus {

    /**
     * 司机工作状态id
     */
    private Long id;
    /**
     * 司机id
     */
    private Long driverId;
    /**
     * 司机工作状态（0收车，1开始接单，2暂停接单）
     */
    private Integer workStatus;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}

