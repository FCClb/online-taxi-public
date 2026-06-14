package com.fc.apidriver.service;

import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * 司机获取验证码
 */
@Service
public class VerificationCodeService {

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //查询 service-driver-user，该手机号的司机是否存在

        //获取验证码

        //todo 调用第三方发送验证码

        //存入redis

        return ResponseResult.success();
    }
}
