package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高德地图-猎鹰轨迹服务-服务管理控制器
 */
@Service
public class ServiceMapService {

    @Autowired
    private ServiceClient serviceClient;

    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name) {

        return serviceClient.add(name);
    }
}
