package com.fc.servicemap.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.service.ServiceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 高德地图-猎鹰轨迹服务-服务管理控制器
 */
@RestController
public class ServiceController {

    @Autowired
    private ServiceMapService serviceMapService;

    /**
     * 创建服务
     *
     * @param name
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(String name) {

        return serviceMapService.add(name);
    }
}
