package com.fc.servicepassengeruser.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.VerificationCodeDTO;
import com.fc.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录或注册
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号passengerPhone: " + passengerPhone);

        return userService.loginOrRegister(passengerPhone);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/user")
    public ResponseResult getUserByPhone(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();

        return userService.getUserByPhone(passengerPhone);
    }

}
