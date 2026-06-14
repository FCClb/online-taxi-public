package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 司机查询 响应结果
 */
@Data
public class DriverUserExistsResponse {

    private String driverPhone;

    private int ifExists;
}
