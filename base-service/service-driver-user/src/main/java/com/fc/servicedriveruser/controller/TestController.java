package com.fc.servicedriveruser.controller;

import com.fc.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @GetMapping("/test")
    public String test() {
        return "service-driver-user test";
    }

    //测试mybatis plus 混合 xml
    @GetMapping("/test-xml")
    public int testXml(String cityCode) {
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        System.out.println(i);
        return i;
    }
}
