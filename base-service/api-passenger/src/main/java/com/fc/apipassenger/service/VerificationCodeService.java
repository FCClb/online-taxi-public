package com.fc.apipassenger.service;

import com.fc.apipassenger.remote.ServicePassengerUserClient;
import com.fc.apipassenger.remote.ServiceVerificationClient;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.IdentityEnum;
import com.fc.internalcommon.constant.TokenTypeEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.VerificationCodeDTO;
import com.fc.internalcommon.response.NumberCodeResponse;
import com.fc.internalcommon.response.TokenResponse;
import com.fc.internalcommon.util.JwtUtils;
import com.fc.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationClient serviceVerificationClient;

    @Autowired
    //如果key，value是String类型，推荐用StringRedisTemplate
    //否则用RedisTemplate
    private StringRedisTemplate stringRedisTemplate;

    //乘客用户服务
    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;


    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //存入redis
        //需要有key，value，过期时间
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone,IdentityEnum.PASSENGER_IDENTITY.getValue());
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        //todo 通过短信服务商（阿里短信服务，腾讯短信通，华信，容联），将对应的验证码发送到手机上

        //统一的返回值
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String passengerPhone, String verificationCode) {
        //1.根据手机号，去redis验证验证码
        // 生成key
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone,IdentityEnum.PASSENGER_IDENTITY.getValue());
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

        //判断原来是否有用户，并进行对应的处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        //颁发令牌
        //认证token
        String accessToken = JwtUtils.generateToken(passengerPhone, IdentityEnum.PASSENGER_IDENTITY.getValue(), TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());
        //刷新token：用于在accessToken过期后刷新accessToken和自身
        String refreshToken = JwtUtils.generateToken(passengerPhone, IdentityEnum.PASSENGER_IDENTITY.getValue(), TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());

        //将token存储到redis中
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityEnum.PASSENGER_IDENTITY.getValue(),TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, IdentityEnum.PASSENGER_IDENTITY.getValue(),TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());
        //refreshToken比accessToken晚过期一天
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return new ResponseResult<>().success(tokenResponse);
    }

}
