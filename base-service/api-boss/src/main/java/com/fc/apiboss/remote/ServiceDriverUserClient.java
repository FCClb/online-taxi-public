package com.fc.apiboss.remote;

import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
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
}
