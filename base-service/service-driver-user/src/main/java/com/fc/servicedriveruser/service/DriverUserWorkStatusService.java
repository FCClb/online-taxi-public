package com.fc.servicedriveruser.service;

import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.DriverUserWorkStatus;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverUserWorkStatusService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeDriverUserWorkStatus(Long driverId, Integer workStatus) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("driver_id", driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        if (driverUserWorkStatuses.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        driverUserWorkStatus.setWorkStatus(workStatus);
        driverUserWorkStatus.setGmtModified(LocalDateTime.now());

        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        return ResponseResult.success("更新司机工作状态成功");
    }

}
