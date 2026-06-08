package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用第三方（高德地图）service
 */
@Service
public class DirectionService {

    /**
     * 根据起始地点经纬度获取 距离和时间
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    @GetMapping("/driving")
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        return ResponseResult.success();
    }
}
