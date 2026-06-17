package com.fc.apidriver.remote;

import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 修改司机用户信息
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 查询
     * 根据 手机号查询司机
     *
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    ResponseResult<DriverUserExistsResponse> checkDriverUser(@PathVariable("driverPhone") String driverPhone);

    /**
     * 查询 根据id查询车辆
     *
     * @param carId
     * @return
     */
    @GetMapping("/car")
    ResponseResult<Car> getCarById(@RequestParam Long carId);

}
