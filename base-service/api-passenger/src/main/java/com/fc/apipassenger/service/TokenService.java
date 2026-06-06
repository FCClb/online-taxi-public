package com.fc.apipassenger.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TokenService {

    /**
     * 双token刷新
     */
    public ResponseResult refreshToken(String refreshToken) {
        //解析refreshToken

        //读取redis中的refreshToken

        //校验refreshToken

        //生成（更新）双token

        return ResponseResult.success();
    }
}
