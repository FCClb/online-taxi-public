package com.fc.servicedriveruser.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     * 新增 终端
     *
     * @param name
     * @return
     */
    @PostMapping("/terminal/add")
    ResponseResult<TerminalResponse> add(@RequestParam String name);
}
