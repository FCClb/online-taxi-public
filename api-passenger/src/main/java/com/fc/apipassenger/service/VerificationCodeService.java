package com.fc.apipassenger.service;

import com.fc.apipassenger.remote.ServiceVerificationClient;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationClient serviceVerificationClient;

    public String generatorCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("调用验证码服务，获取验证码: " + numberCode);


        //存入redis
        System.out.println("存入redis");

        //返回值
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");
        return result.toString();
    }

}
