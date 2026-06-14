package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.apidriver.remote.ServiceVerificationClient;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DriverUserExistsResponse;
import com.fc.internalcommon.response.NumberCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 司机获取验证码
 */
@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient userClient;

    @Autowired
    private ServiceVerificationClient verificationClient;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用service-driver-user服务，查询该手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserResponse = userClient.checkDriverUser(driverPhone);
        DriverUserExistsResponse data = driverUserResponse.getData();
        if (data.getIfExists() == DriverCarConstants.DRIVER_NOT_EXISTS.getState()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone + "的司机存在");

        //获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponseResponseResult = verificationClient.numberCode(6);
        NumberCodeResponse numberCodeResponse = numberCodeResponseResponseResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        log.info("验证码:" + numberCode);
        //todo 调用第三方发送验证码

        //存入redis

        return ResponseResult.success();
    }
}
