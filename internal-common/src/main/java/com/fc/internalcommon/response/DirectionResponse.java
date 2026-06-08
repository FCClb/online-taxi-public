package com.fc.internalcommon.response;

import lombok.Data;

/**
 * 距离（米）以及所需时间（分钟）response
 */
@Data
public class DirectionResponse {

    /**
     * 起始地间距离
     */
    private Integer distance;

    /**
     * 所用时间
     */
    private Integer duration;
}
