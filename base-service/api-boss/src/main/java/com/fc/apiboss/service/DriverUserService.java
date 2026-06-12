package com.fc.apiboss.service;

import com.fc.apiboss.remote.ServiceDriverUserClient;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 增加 乘客用户
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {

        return serviceDriverUserClient.addDriverUser(driverUser);
    }

    /**
     * 修改 乘客用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {

        return serviceDriverUserClient.updateDriverUser(driverUser);
    }
}
