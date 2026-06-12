package com.fc.servicepassengeruser.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.VerificationCodeDTO;
import com.fc.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private PassengerUserService passengerUserService;

    /**
     * 登录或注册
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号passengerPhone: " + passengerPhone);

        return passengerUserService.loginOrRegister(passengerPhone);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable("phone") String phone) {

        return passengerUserService.getUserByPhone(phone);
    }

}
