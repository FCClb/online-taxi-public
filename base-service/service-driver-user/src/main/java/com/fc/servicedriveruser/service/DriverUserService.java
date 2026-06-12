package com.fc.servicedriveruser.service;

import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     * 插入司机用户
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);
        return ResponseResult.success("插入乘客用户成功");
    }

}
