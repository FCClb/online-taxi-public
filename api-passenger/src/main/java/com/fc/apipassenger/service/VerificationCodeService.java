package com.fc.apipassenger.service;

import com.fc.apipassenger.remote.ServiceVerificationClient;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
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

    public ResponseResult generatorCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("get numberCode: " + numberCode);

        //存入redis
        System.out.println("存入redis");
        //需要有key，value，过期时间
        String key = verificationCodePrefix + passengerPhone;
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        //todo 通过短信服务商（阿里短信服务，腾讯短信通，华信，容联），将对应的验证码发送到手机上

        //统一的返回值
        return ResponseResult.success("");
    }

}
