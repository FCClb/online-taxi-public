package com.fc.apidriver.controller;

import com.fc.apidriver.service.PointService;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 高德猎鹰服务-轨迹点上传
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest) {

        return pointService.upload(apiDriverPointRequest);
    }
}
