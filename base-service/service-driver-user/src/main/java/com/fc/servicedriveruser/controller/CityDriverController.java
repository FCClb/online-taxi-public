package com.fc.servicedriveruser.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对司机城市视图表的查询
 * v_city_driver_user_work_status
 */
@RestController
@RequestMapping("/city-driver")
public class CityDriverController {

    @Autowired
    private CityDriverUserService cityDriverUserService;


    /**
     * 查询 当前城市是否有司机
     * @param cityCode 城市编码
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam String cityCode) {

        return cityDriverUserService.isAvailableDriver(cityCode);
    }

}
