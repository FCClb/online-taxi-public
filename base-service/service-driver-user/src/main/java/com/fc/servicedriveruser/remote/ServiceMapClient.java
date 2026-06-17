package com.fc.servicedriveruser.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TraceResponse;
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
    ResponseResult<TerminalResponse> addTerminal(@RequestParam("name") String name, @RequestParam("desc") String desc);

    /**
     * 创建 轨迹
     *
     * @param tid
     * @return
     */
    @PostMapping("/trace/add")
    ResponseResult<TraceResponse> addTrace(@RequestParam String tid);

}
