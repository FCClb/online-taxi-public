package com.fc.apipassenger.controller;

import com.fc.apipassenger.request.VerificationCodeDTO;
import com.fc.apipassenger.service.VerificationCodeService;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码接口
 */
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 验证码接收
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        //todo 后续可以改为日志打印
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数" + passengerPhone);

        return verificationCodeService.generatorCode(passengerPhone);

    }

    /**
     * 验证码校验
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println("用户手机号：" + passengerPhone + " ,验证码：" + verificationCode);

        return verificationCodeService.checkVerificationCode(passengerPhone, verificationCode);
    }

}
