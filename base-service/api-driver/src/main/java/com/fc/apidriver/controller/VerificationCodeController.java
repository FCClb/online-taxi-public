package com.fc.apidriver.controller;

import com.fc.apidriver.service.VerificationCodeService;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司机验证码
 */
@RestController
@Slf4j
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 验证码发送
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String driverPhone = verificationCodeDTO.getDriverPhone();
        log.info("driverPhone: {}", driverPhone);
        return verificationCodeService.checkAndSendVerificationCode(driverPhone);
    }

    /**
     * 验证码校验
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println("司机手机号：" + driverPhone + " ,验证码：" + verificationCode);

        return verificationCodeService.checkVerificationCode(driverPhone, verificationCode);
    }
}
