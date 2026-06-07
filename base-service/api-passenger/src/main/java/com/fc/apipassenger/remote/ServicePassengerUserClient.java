package com.fc.apipassenger.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    /**
     * 登录或注册用户
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/user")
    ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/user/{phone}")
    ResponseResult getUserByPhone(@PathVariable("phone") String phone);
}
