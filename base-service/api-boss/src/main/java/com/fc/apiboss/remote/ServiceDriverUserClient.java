package com.fc.apiboss.remote;

import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.DriverCarBindingRelationship;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 插入司机用户
     *
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 修改司机用户信息
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 新增 车辆
     *
     * @param car
     * @return
     */
    @PostMapping("/car")
    ResponseResult addCar(@RequestBody Car car);

    /**
     * 司机车辆 绑定
     *
     * @param relationship
     * @return
     */
    @PostMapping("/driver-car-binding-relationship/bind")
    ResponseResult bind(@RequestBody DriverCarBindingRelationship relationship);

    /**
     * 司机车辆 解绑
     *
     * @param relationship
     * @return
     */
    @PostMapping("/driver-car-binding-relationship/unbind")
    ResponseResult unbind(@RequestBody DriverCarBindingRelationship relationship);
}
