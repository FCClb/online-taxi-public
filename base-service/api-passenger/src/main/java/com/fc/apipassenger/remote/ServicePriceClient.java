package com.fc.apipassenger.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-price")
public interface ServicePriceClient {

    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     *
     * @param forecastPriceDTO
     * @return
     */
    @PostMapping("/forecast-price")
    ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
