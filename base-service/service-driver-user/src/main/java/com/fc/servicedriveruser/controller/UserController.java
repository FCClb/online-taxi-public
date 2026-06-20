package com.fc.servicedriveruser.controller;

import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DriverUserExistsResponse;
import com.fc.internalcommon.response.OrderDriverResponse;
import com.fc.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 新增 司机用户
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改 司机用户信息
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 查询
     * 根据 手机号查询司机
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult checkDriverUser(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser driverUserDb = driverUserByPhone.getData();

        DriverUserExistsResponse response = new DriverUserExistsResponse();
        if (driverUserDb == null) { //不存在这个司机
            response.setDriverPhone(driverPhone);
            response.setIfExists(DriverCarConstants.DRIVER_NOT_EXISTS.getState());
        } else {    //存在
            response.setDriverPhone(driverUserDb.getDriverPhone());
            response.setIfExists(DriverCarConstants.DRIVER_EXISTS.getState());
        }

        return ResponseResult.success(response);
    }

    /**
     * 查询
     * 根据carId查询可派单的司机信息
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId) {

        return driverUserService.getAvailableDriver(carId);
    }
}
