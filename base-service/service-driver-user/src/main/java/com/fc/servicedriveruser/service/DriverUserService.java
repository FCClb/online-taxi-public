package com.fc.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.dto.DriverCarBindingRelationship;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.DriverUserWorkStatus;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.OrderDriverResponse;
import com.fc.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.fc.servicedriveruser.mapper.DriverUserMapper;
import com.fc.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 新增 司机用户
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);

        //初始化 司机工作状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP.getState());
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success("插入乘客用户成功");
    }

    /**
     * 修改 司机用户信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success("修改乘客用户信息成功");
    }

    /**
     * 查询
     * 根据 手机号查询司机
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);

        if (driverUsers.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }

        return ResponseResult.success(driverUsers.get(0));
    }

    /**
     * 查询
     * 根据carId查询可派单的司机信息
     *
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {

        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", carId);
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());

        DriverCarBindingRelationship relationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        Long driverId = relationship.getDriverId();

        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id", driverId);
        queryWrapper1.eq("work_status", DriverCarConstants.DRIVER_WORK_STATUS_START.getState());


        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper1);
        if (null == driverUserWorkStatus) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(), CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        } else {
            QueryWrapper<DriverUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", driverId);
            DriverUser driverUser = driverUserMapper.selectOne(queryWrapper2);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());

            return ResponseResult.success(orderDriverResponse);
        }
    }

}
