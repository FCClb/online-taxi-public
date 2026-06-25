package com.fc.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.dto.DriverCarBindingRelationship;
import com.fc.internalcommon.dto.DriverUser;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.fc.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    private DriverUserMapper driverUserMapper;


    /**
     * 司机车辆 绑定
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {

        //判断，如果司机和车辆已经绑定，则不允许再次绑定（否则会新增一条绑定记录）
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<DriverCarBindingRelationship>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());

        //司机和车辆已经绑定
        Integer count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());

        //司机已经绑定
        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_BIND_EXISTS.getValue());
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());

        //车辆已经绑定
        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(), CommonStatusEnum.CAR_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND.getState());

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("司机车辆绑定成功");
    }

    /**
     * 司机车辆 解绑
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        //司机和车辆未绑定
        if (driverCarBindingRelationships.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }
        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        relationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND.getState());
        LocalDateTime now = LocalDateTime.now();
        relationship.setUnBindingTime(now);

        driverCarBindingRelationshipMapper.updateById(relationship);
        return ResponseResult.success("司机车辆解绑成功");
    }

    /**
     * 查询司机车辆绑定关系
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationshipByDriverPhone(String driverPhone) {
        QueryWrapper<DriverUser> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
        driverCarBindingRelationshipQueryWrapper.eq("driver_phone", driverPhone);
        DriverUser driverUser = driverUserMapper.selectOne(driverCarBindingRelationshipQueryWrapper);
        Long driverId = driverUser.getId();

        QueryWrapper<DriverCarBindingRelationship> driverUserWorkStatusQueryWrapper = new QueryWrapper<>();
        driverUserWorkStatusQueryWrapper.eq("driver_id", driverId);
        driverUserWorkStatusQueryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND.getState());
        DriverCarBindingRelationship relationship = driverCarBindingRelationshipMapper.selectOne(driverUserWorkStatusQueryWrapper);


        return ResponseResult.success(relationship);
    }


}
