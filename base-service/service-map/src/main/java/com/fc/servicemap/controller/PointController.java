package com.fc.servicemap.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.PointRequest;
import com.fc.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 高德地图-猎鹰轨迹服务-轨迹点上传
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest) {

        return pointService.upload(pointRequest);
    }
}
