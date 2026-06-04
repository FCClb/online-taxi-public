package com.fc.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        //判断token异常
        try {
            JwtUtils.parseToken(token);
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

        //如果异常，要给前端数据响应
        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }

}
