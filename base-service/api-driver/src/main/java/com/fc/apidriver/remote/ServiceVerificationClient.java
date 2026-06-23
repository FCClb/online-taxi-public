package com.fc.apidriver.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用
 * 验证码服务
 */
@FeignClient(name = "service-verification")
public interface ServiceVerificationClient {

    @GetMapping("/numberCode/{size}")
    ResponseResult<NumberCodeResponse> numberCode(@PathVariable int size);

}
