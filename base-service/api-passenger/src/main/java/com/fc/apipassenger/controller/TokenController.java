package com.fc.apipassenger.controller;

import com.fc.apipassenger.service.TokenService;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 双token刷新
     * @param tokenResponse 带有accessToken和refreshToken
     * @return
     */
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {

        return tokenService.refreshToken(tokenResponse.getRefreshToken());
    }

}
