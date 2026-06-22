package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 查询 当前城市是否有司机
     *
     * @param cityCode 城市编码
     * @return
     */
    @GetMapping("/city-driver/is-available-driver")
    ResponseResult<Boolean> isAvailableDriver(@RequestParam String cityCode);

    /**
     * 查询
     * 根据carId查询可派单的司机信息
     *
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);

    /**
     * 查询 根据id查询车辆
     *
     * @param carId
     * @return
     */
    @GetMapping("/car")
    ResponseResult<Car> getCarById(@RequestParam Long carId);
}
