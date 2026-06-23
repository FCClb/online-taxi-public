package com.fc.serviceprice.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import com.fc.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 价格接口
 */
@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;

    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     * @param forecastPriceDTO
     * @return
     */
    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {

        return priceService.forecastPrice(forecastPriceDTO.getDepLongitude(), forecastPriceDTO.getDepLatitude(),
                forecastPriceDTO.getDestLongitude(), forecastPriceDTO.getDestLatitude(),
                forecastPriceDTO.getCityCode(), forecastPriceDTO.getVehicleType());
    }

    /**
     * 计算实际价格
     *
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    @PostMapping("/calculate-price")
    public ResponseResult<Double> calculatePrice(@RequestParam Integer distance, @RequestParam Integer duration, @RequestParam String cityCode, @RequestParam String vehicleType) {

        return priceService.calculatePrice(distance, duration, cityCode, vehicleType);
    }
}
