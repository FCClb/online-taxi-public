package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.DriverUserWorkStatus;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 修改 司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateDriverUser(driverUser);
    }

    /**
     * 修改司机工作状态
     *
     * @param driverUserWorkStatus
     * @return
     */
    public ResponseResult updateDriverUserWorkStatus(DriverUserWorkStatus driverUserWorkStatus) {
        return serviceDriverUserClient.changeDriverUserWorkStatus(driverUserWorkStatus);
    }

}
