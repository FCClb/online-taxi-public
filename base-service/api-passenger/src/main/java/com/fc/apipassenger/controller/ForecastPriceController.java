package com.fc.apipassenger.controller;

import com.fc.apipassenger.service.ForecastPriceService;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 预估价格接口
 */
@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     * @param forecastPriceDTO
     * @return
     */
    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        log.info("forecastPriceDTO: {}", forecastPriceDTO);

        return forecastPriceService.forecastPrice(forecastPriceDTO.getDepLongitude(), forecastPriceDTO.getDepLatitude(),
                forecastPriceDTO.getDestLongitude(), forecastPriceDTO.getDestLatitude(),
                forecastPriceDTO.getCityCode(), forecastPriceDTO.getVehicleType());
    }
}
