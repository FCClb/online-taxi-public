package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.internalcommon.dto.*;
import com.fc.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 查询司机车辆绑定关系
     *
     * @return
     */
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone) {
        //根据driverPhone查询司机车辆绑定信息
        return serviceDriverUserClient.getDriverCarBindingRelationship(driverPhone);
    }

}
