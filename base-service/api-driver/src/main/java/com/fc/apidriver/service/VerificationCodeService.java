package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.apidriver.remote.ServiceVerificationClient;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.DriverCarConstants;
import com.fc.internalcommon.constant.IdentityEnum;
import com.fc.internalcommon.constant.TokenTypeEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.DriverUserExistsResponse;
import com.fc.internalcommon.response.NumberCodeResponse;
import com.fc.internalcommon.response.TokenResponse;
import com.fc.internalcommon.util.JwtUtils;
import com.fc.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

    /**
     * 校验验证码
     * @param driverPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String driverPhone, String verificationCode) {
        //1.根据手机号，去redis验证验证码
        // 生成key
        String key = RedisPrefixUtils.generateKeyByPhone(driverPhone,IdentityEnum.DRIVER_IDENTITY.getValue());
        //根据key去获取value
        String value = stringRedisTemplate.opsForValue().get(key);

        //校验验证码
        if (StringUtils.isBlank(value)) {   //判断是否为空
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(value)) {   //判断是否和redis中的验证码一致
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        System.out.println("校验验证码成功");

        //颁发令牌
        //认证token
        String accessToken = JwtUtils.generateToken(driverPhone, IdentityEnum.DRIVER_IDENTITY.getValue(), TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());
        //刷新token：用于在accessToken过期后刷新accessToken和自身
        String refreshToken = JwtUtils.generateToken(driverPhone, IdentityEnum.DRIVER_IDENTITY.getValue(), TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());

        //将token存储到redis中
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityEnum.DRIVER_IDENTITY.getValue(),TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityEnum.DRIVER_IDENTITY.getValue(),TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());
        //refreshToken比accessToken晚过期一天
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return new ResponseResult<>().success(tokenResponse);
    }
}
