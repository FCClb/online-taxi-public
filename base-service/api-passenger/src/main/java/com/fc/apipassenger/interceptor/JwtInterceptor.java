package com.fc.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fc.internalcommon.constant.TokenTypeEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TokenResult;
import com.fc.internalcommon.util.JwtUtils;
import com.fc.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        //判断token异常
        TokenResult tokenResult = JwtUtils.checkToken(token);

        //从redis中取出token
        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            //拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());

            //从redis中取出accessToken
            String tokenRedis = redisTemplate.opsForValue().get(tokenKey);

            //  redis中没有                            校验传入的token和从redis中取出的token是否一致
            if ((StringUtils.isBlank(tokenRedis)) || (!tokenRedis.trim().equals(token.trim()))) {
                resultString = "token invalid";
                result = false;
            }

        }

        //如果异常，要给前端数据响应
        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }

}
