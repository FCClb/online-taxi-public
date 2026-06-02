package com.fc.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtils {

    //盐
    private static final String SIGN = "@FCClb";

    private static final String JWT_KEY = "passengerPhone";

    //生成token
    public static String generateToken(String passengerPhone) {
        JWTCreator.Builder builder = JWT.create();

        //整合JWT_KEY
        builder.withClaim(JWT_KEY, passengerPhone);

        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); //找到一天后的时间点
        Date date = calendar.getTime();
        //整合过期时间
        builder.withExpiresAt(date);

        //生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static String parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }

    public static void main(String[] args) {
        String s = generateToken("15503478318");

        System.out.println("生成的token：" + s);
        System.out.println("==========");

        System.out.println("解析token：" + parseToken(s));


    }
}
