package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用第三方（高德地图）service
 */
@Service
public class DirectionService {

    /**
     * 根据起始地点经纬度获取 距离（米）和时间（分钟）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    @GetMapping("/driving")
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(12);

        return ResponseResult.success(directionResponse);
    }
}
