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

        TokenResult tokenResult = null;
        //判断token异常
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultString = "toke sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token expired";
            result = false;
        } catch (AlgorithmMismatchException e) {
            resultString = "algorithm mismatch";
            result = false;
        }catch (Exception e) {
            resultString = "token invalid";
            result = false;
        }

        //从redis中取出token
        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenTypeEnum.ACCESS_TOKEN_TYPE.getTokenType());

            String tokenRedis = redisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)) {  //redis中没有
                resultString = "token invalid";
                result = false;
            } else {    //校验传入的token和从redis中取出的token是否一致
                if (!tokenRedis.trim().equals(token)) { //不相等
                    resultString = "token invalid";
                    result = false;
                }
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
