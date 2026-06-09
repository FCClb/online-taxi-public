package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 距离（米）以及所需时间（秒）response
 */
@Data
public class DirectionResponse {

    /**
     * 起始地间距离（米）
     */
    private Integer distance;

    /**
     * 所用时间（秒）
     */
    private Integer duration;
}
