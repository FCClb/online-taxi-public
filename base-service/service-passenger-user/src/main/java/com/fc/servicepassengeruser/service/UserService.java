package com.fc.servicepassengeruser.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicepassengeruser.dto.PassengerUser;
import com.fc.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("UserService 被调用，手机号passengerPhone=" + passengerPhone);

        //todo 根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0 ? "无记录" : passengerUsers.get(0).getPassengerName());

        //todo 判断用户信息是否存在

        //todo 如果不存在，则插入用户信息

        return ResponseResult.success(passengerPhone);
    }
}
