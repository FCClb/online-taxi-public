package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.internalcommon.response.TrsearchResponse;
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

    /**
     * 查询轨迹信息（轨迹信息包括经纬度点，里程，时间等信息）
     *
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    @PostMapping("/terminal/trsearch")
    ResponseResult<TrsearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);
}
