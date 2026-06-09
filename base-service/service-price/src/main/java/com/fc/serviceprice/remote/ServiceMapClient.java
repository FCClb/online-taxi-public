package com.fc.serviceprice.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import com.fc.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 地图服务
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     * 根据起始地点经纬度获取 距离和时间
     *
     * @param forecastPriceDTO
     * @return
     */
    @PostMapping("/direction/driving")
    ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
