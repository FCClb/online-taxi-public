package com.fc.apipassenger.controller;

import com.fc.apipassenger.request.VerificationCodeDTO;
import com.fc.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码接收
 */
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public String verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        //todo 后续可以改为日志打印
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数" + passengerPhone);

        return verificationCodeService.generatorCode(passengerPhone);

    }
}
