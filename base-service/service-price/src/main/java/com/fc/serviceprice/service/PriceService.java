package com.fc.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ForecastPriceDTO;
import com.fc.internalcommon.response.DirectionResponse;
import com.fc.internalcommon.response.ForecastPriceResponse;
import com.fc.internalcommon.util.BigDecimalUtils;
import com.fc.serviceprice.mapper.PriceRuleMapper;
import com.fc.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预估价格service
 */
@Service
@Slf4j
public class PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    /**
     * 根据 出发地和目的地的经纬度 计算预估价格
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,
                                        String cityCode, String vehicleType) {

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
        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离、时长和计价规则，计算价格");
        double price = getPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);
        forecastPriceResponse.setFareType(priceRule.getFareType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());

        return ResponseResult.success(forecastPriceResponse);
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
    public ResponseResult calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType) {
        //查询计价规则
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }

        PriceRule priceRule = priceRules.get(0);

        double price = getPrice(distance, duration, priceRule);

        return ResponseResult.success(price);
    }

    /**
     * 根据距离和时长，按照传入的priceRule计算价格
     *
     * @param distance  距离
     * @param duration  时长
     * @param priceRule 计价规则
     * @return
     */
    public double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        //起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price, startFare);

        //***里程费
        //总里程 km
        double distanceMile = BigDecimalUtils.divide(distance, 1000);
        //起步里程
        double startMile = (double)priceRule.getStartMile();
        //超出起步里程的里程
        double distanceSubtract = BigDecimalUtils.subtract(distanceMile, startMile);
        //最终收费的里程数（不足起步里程要算作0）
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        //每公里单价
        double unitPricePerMile = priceRule.getUnitPricePerMile();

        double mileFare = BigDecimalUtils.multiply(mile, unitPricePerMile);
        price = BigDecimalUtils.add(price, mileFare);

        //***时长费
        //时长（单位min）
        double time = BigDecimalUtils.divide(duration, 60);
        //计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();

        double timeFare = BigDecimalUtils.multiply(time, unitPricePerMinute);
        price = BigDecimalUtils.add(price, timeFare);

        return price;
    }

    //for test
//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setStartMile(3);
//        priceRule.setStartFare(10.0);
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
////        预计结果：31.3
//        System.out.println(getPrice(6500,1800,priceRule));
//    }

}
