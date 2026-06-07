package com.fc.apipassenger.service;

import com.fc.internalcommon.dto.PassengerUser;
import com.fc.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    /**
     * 查询用户信息
     *
     * @param accessToken
     * @return
     */
    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("getUserByAccessToken: " + accessToken);
        //解析accessToken，拿到手机号

        //根据手机号查询用户信息

        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("test");
        passengerUser.setProfilePhoto("photo");

        return ResponseResult.success(passengerUser);
    }
}
