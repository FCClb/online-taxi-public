package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.apidriver.remote.ServiceVerificationClient;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.constant.IdentityEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DriverUserExistsResponse;
import com.fc.internalcommon.response.NumberCodeResponse;
import com.fc.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验该手机号司机是否存在，若存在则发送验证码并存入redis
     * @param driverPhone
     * @return
     */
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
        String key = RedisPrefixUtils.generateKeyByPhone(driverPhone, IdentityEnum.DRIVER_IDENTITY.getValue());
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        return ResponseResult.success(driverPhone);
    }
}
