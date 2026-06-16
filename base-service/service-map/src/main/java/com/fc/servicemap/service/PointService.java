package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.PointRequest;
import com.fc.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高德地图-猎鹰轨迹服务-轨迹点上传
 */
@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest) {

        return pointClient.upload(pointRequest);
    }
}
