package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
}
