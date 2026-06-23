package com.fc.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.IdentityEnum;
import com.fc.internalcommon.constant.OrderConstants;
import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.OrderInfo;
import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.internalcommon.request.PriceRuleIsNewRequest;
import com.fc.internalcommon.request.PushRequest;
import com.fc.internalcommon.response.OrderDriverResponse;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.internalcommon.response.TrsearchResponse;
import com.fc.internalcommon.util.RedisPrefixUtils;
import com.fc.serviceorder.mapper.OrderMapper;
import com.fc.serviceorder.remote.ServiceDriverUserClient;
import com.fc.serviceorder.remote.ServiceMapClient;
import com.fc.serviceorder.remote.ServicePriceClient;
import com.fc.serviceorder.remote.ServiceSsePushClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        //判断城市是否有司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("测试城市是否有司机" + availableDriver.getData());
        if (!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getValue());
        }

        //判断计价规则的版本是否为最新
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> isNew = servicePriceClient.isNew(priceRuleIsNewRequest);
        if (!(isNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //判断是否有正在进行的订单，有则不允许下单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        //判断下单的设备是否在黑名单
        if (isBlack(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());
        }

        //判断下单的城市和计价规则是否正常 弃用
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

        //定时任务的处理
        for (int i = 0; i < 6; i++) {
            //实时订单派单
            int result = dispatchRealTimeOrder(orderInfo);

            int count = i + 1;
            log.info("===第" + count + "次尝试派单===");
            if (result == 1) {  //派单成功 则跳出循环
                break;
            }

            //等待20秒
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        return ResponseResult.success("订单");
    }

    /**
     * 实时订单派单
     * @param orderInfo
     * @return 派单成功则返回 1
     */
    public int dispatchRealTimeOrder(OrderInfo orderInfo) {
        int result = 0; //标记 派单是否成功

        String depLongitude = orderInfo.getDepLongitude();
        String depLatitude = orderInfo.getDepLatitude();
        String center = depLatitude + "," + depLongitude;

        ArrayList<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        ResponseResult<List<TerminalResponse>> listResponseResult = null;

        // goto
        radius:

        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.aroundsearch(center, radius);

            log.info("在半径为" + radius + "的范围内，寻找车辆，响应结果：" + JSONArray.fromObject(listResponseResult.getData()).toString());

            //获得终端
            List<TerminalResponse> data = listResponseResult.getData();

            for (TerminalResponse terminalResponse : data) {
                Long carId = terminalResponse.getCarId();
                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();
                //查询 是否有可派单司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                    log.info("车辆Id" + carId+"的司机不可派单");
                    continue;
                } else {
                    log.info("找到了可派单的司机，车辆Id为:" + carId);

                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String vehicleTypeFromCar = orderDriverResponse.getVehicleType();
                    //判断车型是否符合
                    if (!vehicleTypeFromCar.trim().equals(orderInfo.getVehicleType().trim())) {
                        log.info("车型不符合");
                        continue;
                    }

                    //判断司机 是否有正在进行的订单
                    if (isDriverOrderGoingOn(driverId) > 0) {   //有订单
                        continue;
                    }
                    //没订单，该司机可派单，不再进行司机的查找

                    //直接给司机派单
                    //1.当前司机信息
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(orderDriverResponse.getDriverPhone());
                    orderInfo.setLicenseId(orderDriverResponse.getLicenseId());

                    //2.当前车辆信息
                    orderInfo.setCarId(carId);
                    orderInfo.setVehicleNo(orderDriverResponse.getVehicleNo());

                    //3.地图信息
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    //司机接单
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER.getCode());

                    orderMapper.updateById(orderInfo);

                    //通知司机
                    PushRequest driverPushRequest = new PushRequest();
                    driverPushRequest.setUserId(driverId);
                    driverPushRequest.setIdentity(IdentityEnum.DRIVER_IDENTITY.getValue());

                    ObjectNode driverObjectNode = objectMapper.createObjectNode();
                    driverObjectNode.put("passengerId", String.valueOf(orderInfo.getPassengerId()));
                    driverObjectNode.put("passengerPhone", orderInfo.getPassengerPhone());
                    driverObjectNode.put("departure", orderInfo.getDeparture());
                    driverObjectNode.put("depLongitude", orderInfo.getDepLongitude());
                    driverObjectNode.put("depLatitude", orderInfo.getDepLatitude());

                    driverObjectNode.put("destination",orderInfo.getDestination());
                    driverObjectNode.put("destLongitude",orderInfo.getDestLongitude());
                    driverObjectNode.put("destLatitude",orderInfo.getDestLatitude());
                    try {
                        String ContentText = objectMapper.writeValueAsString(driverObjectNode);
                        log.info("发送给司机的消息" + ContentText);
                        driverPushRequest.setContent(ContentText);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    serviceSsePushClient.push(driverPushRequest);

                    //通知乘客
                    PushRequest passengerPushRequest = new PushRequest();
                    passengerPushRequest.setUserId(orderInfo.getPassengerId());
                    passengerPushRequest.setIdentity(IdentityEnum.PASSENGER_IDENTITY.getValue());

                    ObjectNode passengerObjectNode = objectMapper.createObjectNode();
                    passengerObjectNode.put("driverId", String.valueOf(orderInfo.getDriverId()));
                    passengerObjectNode.put("driverPhone", orderInfo.getDriverPhone());
                    passengerObjectNode.put("vehicleNo", orderInfo.getVehicleNo());

                    ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
                    passengerObjectNode.put("brand", carById.getData().getBrand());
                    passengerObjectNode.put("model", carById.getData().getModel());
                    passengerObjectNode.put("vehicleColor", carById.getData().getVehicleColor());

                    passengerObjectNode.put("receiveOrderCarLongitude",orderInfo.getReceiveOrderCarLongitude());
                    passengerObjectNode.put("receiveOrderCarLatitude",orderInfo.getReceiveOrderCarLatitude());
                    passengerObjectNode.put("destLatitude",orderInfo.getDestLatitude());
                    try {
                        String passengerContentText = objectMapper.writeValueAsString(passengerObjectNode);
                        log.info("发送给乘客的消息" + passengerContentText);
                        passengerPushRequest.setContent(passengerContentText);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    serviceSsePushClient.push(passengerPushRequest);

                    result = 1; //标记 派单成功

                    break radius;

                }
            }
        }

        return result;
    }

    /**
     * 判断乘客是否有正在进行的订单
     * @param passengerId
     * @return
     */
    public Integer isPassengerOrderGoingOn(Long passengerId) {
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
     * 判断司机是否有正在进行的订单
     * @param driverId
     * @return
     */
    public Integer isDriverOrderGoingOn(Long driverId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER.getCode())
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER.getCode())
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE.getCode())
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER.getCode())
        );
        Integer validOrderNumber = orderMapper.selectCount(queryWrapper);
        log.info("司机Id：" + driverId + ",正在进行订单的数量：" + validOrderNumber);
        return validOrderNumber;

    }

    /**
     * 弃用
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


    /**
     * 司机前往接驾乘客 修改订单状态
     * @param orderRequest
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {

        Long orderId = orderRequest.getOrderId();
        LocalDateTime toPickUpPassengerTime = orderRequest.getToPickUpPassengerTime();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);

        orderInfo.setToPickUpPassengerTime(toPickUpPassengerTime);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setOrderStatus(OrderConstants.DRIVER_TO_PICK_UP_PASSENGER.getCode());

        orderMapper.updateById(orderInfo);

        return ResponseResult.success("司机前往接驾乘客 修改订单状态");
    }

    /**
     * 司机到达上车点 修改订单状态
     * @param orderRequest
     * @return
     */
    public ResponseResult arrivedDeparture(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);

        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstants.DRIVER_ARRIVED_DEPARTURE.getCode());

        orderMapper.updateById(orderInfo);
        return ResponseResult.success("司机到达上车点 修改订单状态");
    }

    /**
     * 司机接到乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);

        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setOrderStatus(OrderConstants.PICK_UP_PASSENGER.getCode());

        orderMapper.updateById(orderInfo);
        return ResponseResult.success("司机接到乘客 修改订单状态");
    }

    /**
     * 乘客到达目的地/行程终止 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult passengerGetoff(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);

        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(orderRequest.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());
        orderInfo.setOrderStatus(OrderConstants.PASSENGER_GET_OFF.getCode());

        //更新订单 载客里程(米) 载客时长(分)
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(orderInfo.getCarId());

        long starttime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info("starttime:" + starttime + " endtime:" + endtime);
        ResponseResult<TrsearchResponse> trsearch = serviceMapClient.trsearch(carById.getData().getTid(), starttime, endtime);

        TrsearchResponse data = trsearch.getData();
        Long driveMile = data.getDriveMile();
        Long driveTime = data.getDriveTime();
        orderInfo.setDriveMile(driveMile);
        orderInfo.setDriveTime(driveTime);

        orderMapper.updateById(orderInfo);

        return ResponseResult.success("乘客到达目的地/行程终止 修改订单状态");
    }
}
