package com.fc.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计价规则 管理
 */
@Service
public class PriceRuleService {

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    /**
     * 新增 计价规则
     *
     * @param priceRule
     * @return
     */
    public ResponseResult add(PriceRule priceRule) {

        //拼接 fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;

        priceRule.setFareType(fareType);

        //添加版本号
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        if (priceRules.size() > 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_EXISTS.getValue());
        }

        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("创建计价规则");
    }

    /**
     * 更新 计价规则
     *
     * @param priceRule
     * @return
     */
    public ResponseResult edit(PriceRule priceRule) {

        //拼接 fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;

        priceRule.setFareType(fareType);

        //添加版本号
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        Integer fareVersion = 0;

        if (priceRules.size() > 0) {
            PriceRule latestPriceRule = priceRules.get(0);
            Double startFare = latestPriceRule.getStartFare();
            Integer startMile = latestPriceRule.getStartMile();
            Double unitPricePerMile = latestPriceRule.getUnitPricePerMile();
            Double unitPricePerMinute = latestPriceRule.getUnitPricePerMinute();

            if (startFare.equals(priceRule.getStartFare())
                    && startMile.equals(priceRule.getStartMile())
                    && unitPricePerMile.equals(priceRule.getUnitPricePerMile())
                    && unitPricePerMinute.equals(priceRule.getUnitPricePerMinute())) {

                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EDIT.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EDIT.getValue());
            }

            fareVersion = latestPriceRule.getFareVersion();
        }

        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("更新计价规则");
    }
}
