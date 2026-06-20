package com.fc.servicedriveruser.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对司机城市视图表的查询
 * v_city_driver_user_work_status
 */
@Service
public class CityDriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     * 查询 当前城市是否有司机
     * @param cityCode 城市编码
     * @return
     */
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if (i > 0) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.success(false);
        }

    }
}
