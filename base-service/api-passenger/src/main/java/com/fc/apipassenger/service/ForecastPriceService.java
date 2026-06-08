package com.fc.apipassenger.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 预估价格service
 */
@Service
@Slf4j
public class ForecastPriceService {


    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        log.info("调用计价服务，计算价格");

        ForecastPriceResponse response = new ForecastPriceResponse();
        response.setPrice(12.12);

        return ResponseResult.success(response);
    }
}
