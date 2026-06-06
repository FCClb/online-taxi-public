package com.fc.apipassenger.service;

import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.constant.TokenTypeEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TokenResult;
import com.fc.internalcommon.response.TokenResponse;
import com.fc.internalcommon.util.JwtUtils;
import com.fc.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 双token刷新
     */
    public ResponseResult refreshToken(String refreshTokenSrc) {
        //解析refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        //读取redis中的refreshToken
        //拼接key
        String key = RedisPrefixUtils.generateTokenKey(phone, identity, TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());
        //从redis中获取refreshToken
        String refreshTokenSrcRedis = stringRedisTemplate.opsForValue().get(key);

        //校验refreshToken
        if ((StringUtils.isBlank(refreshTokenSrcRedis)) || (!refreshTokenSrcRedis.trim().equals(refreshTokenSrc.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        //生成（更新）双token
        String accessToken = JwtUtils.generateToken(phone, identity, TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());
        String refreshToken = JwtUtils.generateToken(phone, identity, TokenTypeEnum.REFRESH_TOKEN_TYPE.getTokenType());

        //把更新后的双token再存入redis中
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils.generateTokenKey(phone, identity, TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType()), accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(key, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
