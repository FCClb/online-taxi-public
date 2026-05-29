package com.fc.apipassenger.service;

import com.fc.apipassenger.remote.ServiceVerificationClient;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.NumberCodeResponse;
import com.fc.internalcommon.response.TokenResponse;
import net.sf.json.JSONObject;
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

    //乘客验证码的前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    //如果key，value是String类型，推荐用StringRedisTemplate
    //否则用RedisTemplate
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 工具方法
     * 根据手机号生成Key
     * @param passengerPhone
     * @return
     */
    private String generateKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone) {
        //调用验证码服务，获取验证码
//        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
//        System.out.println("get numberCode: " + numberCode);

        //存入redis
//        System.out.println("存入redis");
        //需要有key，value，过期时间
        String key = generateKeyByPhone(passengerPhone);
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
//        System.out.println("根据手机号，去redis验证验证码");
            //生成key
        String key = generateKeyByPhone(passengerPhone);
            //根据key去获取value
        String value = stringRedisTemplate.opsForValue().get(key);
//        System.out.println("redis中获得的value: " + value);

        //校验验证码
        if (StringUtils.isBlank(value)) {   //判断是否为空
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(value)) {   //判断是否和redis中的验证码一致
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        System.out.println("校验验证码");

        //判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");

        //颁发令牌
        System.out.println("颁发令牌");

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");

        return new ResponseResult<>().success(tokenResponse);
    }

}
