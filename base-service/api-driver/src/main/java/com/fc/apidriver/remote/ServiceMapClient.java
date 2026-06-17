package com.fc.apidriver.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 地图服务
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     * 高德地图-猎鹰轨迹服务-轨迹点上传
     * @param pointRequest
     * @return
     */
    @PostMapping("/point/upload")
    ResponseResult upload(@RequestBody PointRequest pointRequest);
}
