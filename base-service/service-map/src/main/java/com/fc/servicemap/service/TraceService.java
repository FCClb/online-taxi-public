package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TraceResponse;
import com.fc.servicemap.remote.TraceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高德地图-猎鹰轨迹服务-轨迹管理
 */
@Service
public class TraceService {

    @Autowired
    private TraceClient traceClient;

    public ResponseResult<TraceResponse> add(String tid) {

        return traceClient.add(tid);
    }

}
