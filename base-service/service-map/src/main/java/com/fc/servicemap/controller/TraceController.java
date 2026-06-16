package com.fc.servicemap.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TraceResponse;
import com.fc.servicemap.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 高德地图-猎鹰轨迹服务-轨迹管理
 */
@RestController
@RequestMapping("/trace")
public class TraceController {

    @Autowired
    private TraceService traceService;

    /**
     * 创建 轨迹
     * @param tid
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<TraceResponse> add(@RequestParam String tid) {

        return traceService.add(tid);
    }

}
