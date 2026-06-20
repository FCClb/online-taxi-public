package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     * 周边搜索终端
     *
     * @param center
     * @param radius
     * @return
     */
    @PostMapping("/terminal/aroundsearch")
    ResponseResult<List<TerminalResponse>> aroundsearch(@RequestParam String center,@RequestParam Integer radius);
}
