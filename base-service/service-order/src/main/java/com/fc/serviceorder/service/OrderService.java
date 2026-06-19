package com.fc.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.OrderConstants;
import com.fc.internalcommon.dto.OrderInfo;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.internalcommon.util.RedisPrefixUtils;
import com.fc.serviceorder.mapper.OrderMapper;
import com.fc.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 订单管理
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {
        //需要判断计价规则的版本是否为最新
        ResponseResult<Boolean> isNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(isNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //判断下单的设备是否在黑名单
        String deviceCode = orderRequest.getDeviceCode();
        if (isBlack(deviceCode)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());
        }

        //判断有正在进行的订单，不允许下单
        if (isOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

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
     * @param deviceCode
     * @return
     */
    private boolean isBlack(String deviceCode) {
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

}
