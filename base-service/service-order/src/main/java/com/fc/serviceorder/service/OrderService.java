package com.fc.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.OrderConstants;
import com.fc.internalcommon.dto.OrderInfo;
import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.internalcommon.util.RedisPrefixUtils;
import com.fc.serviceorder.mapper.OrderMapper;
import com.fc.serviceorder.remote.ServiceDriverUserClient;
import com.fc.serviceorder.remote.ServiceMapClient;
import com.fc.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单管理
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        //测试城市是否有司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("测试城市是否有司机" + availableDriver.getData());
        if (!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getValue());
        }

        //判断计价规则的版本是否为最新
        ResponseResult<Boolean> isNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(isNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //判断下单的设备是否在黑名单
        if (isBlack(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());
        }

        //判断是否有正在进行的订单，有则不允许下单
        if (isOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        //判断下单的城市和计价规则是否正常
//        if (!isPriceRuleExists(orderRequest)) {
//            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue());
//        }

        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START.getCode());

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderMapper.insert(orderInfo);

        return ResponseResult.success("创建订单");
    }

    /**
     * 实时订单派单
     * @param orderInfo
     * @return
     */
    public ResponseResult dispatchRealTimeOrder(OrderInfo orderInfo) {

        String depLongitude = orderInfo.getDepLongitude();
        String depLatitude = orderInfo.getDepLatitude();
        String center = depLatitude + "," + depLongitude;

        ArrayList<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        ResponseResult<List<TerminalResponse>> listResponseResult = null;

        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.aroundsearch(center, radius);

            log.info("在半径为" + radius + "的范围内，寻找车辆，响应结果：" + JSONArray.fromObject(listResponseResult.getData()).toString());

            //获得终端
            JsonNode jsonNode = objectMapper.valueToTree(listResponseResult.getData());
            for (JsonNode node : jsonNode) {
                String carId = node.path("carId").asText();
                String tid = node.path("tid").asText();
                log.info("搜索到的车辆为：carId:"+carId+",tid:"+tid);
            }

            //解析终端

            //根据解析出的终端，查询车辆信息

            //找到符合的车辆，进行派单

            //如果派单成功，则退出循环
        }


        return null;
    }

    /**
     * 判断是否有正在进行的订单
     * @param passengerId
     * @return
     */
    public Integer isOrderGoingOn(Long passengerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START.getCode())
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER.getCode())
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER.getCode())
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE.getCode())
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER.getCode())
                .or().eq("order_status", OrderConstants.PASSENGER_GET_OFF.getCode())
                .or().eq("order_status", OrderConstants.TO_START_PAY.getCode())
        );
        Integer validOrderNumber = orderMapper.selectCount(queryWrapper);

        return validOrderNumber;

    }

    /**
     * 判断下单的设备是否在黑名单
     * @param orderRequest
     * @return
     */
    private boolean isBlack(OrderRequest orderRequest) {
        //获取设备唯一码
        String deviceCode = orderRequest.getDeviceCode();
        //生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
        //设置key（查询原来有没有）
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if (aBoolean) { //原来有记录
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if (i >= 2) {
                //当前设备超过下单次数
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {    //原来没有记录
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1L, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 判断下单的城市和计价规则是否正常
     *
     * @param orderRequest
     * @return
     */
    private boolean isPriceRuleExists(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifExists(priceRule);
        return booleanResponseResult.getData();
    }


}
