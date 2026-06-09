package com.fc.serviceprice.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import com.fc.internalcommon.response.DirectionResponse;
import com.fc.internalcommon.response.ForecastPriceResponse;
import com.fc.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 预估价格service
 */
@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        log.info("调用地图服务，查询地图和时长");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);

        ResponseResult<DirectionResponse> driving = serviceMapClient.driving(forecastPriceDTO);
        Integer distance = driving.getData().getDistance();
        Integer duration = driving.getData().getDuration();

        log.info("读取计价规则");

        log.info("根据距离、时长和计价规则，计算价格");

        return driving;
    }
}
