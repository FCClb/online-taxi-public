package com.fc.apidriver.controller;

import com.fc.apidriver.service.DriverUserService;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.DriverUserWorkStatus;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 修改 司机用户信息
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 修改司机工作状态
     *
     * @param driverUserWorkStatus
     * @return
     */
    @PostMapping("/driver-user-work-status")
    public ResponseResult updateDriverUserWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus) {

        return driverUserService.updateDriverUserWorkStatus(driverUserWorkStatus);
    }

}
