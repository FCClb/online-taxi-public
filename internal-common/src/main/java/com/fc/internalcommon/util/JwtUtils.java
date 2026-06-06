package com.fc.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fc.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 */
public class JwtUtils {

    //盐
    private static final String SIGN = "@FCClb";

    private static final String JWT_KEY_PHONE = "phone";

    //司机是0，乘客是1
    private static final String JWT_KEY_IDENTITY = "identity";

    //token类型
    private static final String JWT_TOKEN_TYPE = "tokenType";

    //token创建时间（加入创建jwt过程，防止每次刷新的token都一样）
    private static final String JWT_TOKEN_TIME = "tokenTime";


    //生成token
    public static String generateToken(String Phone, String identity, String tokenType) {

        JWTCreator.Builder builder = JWT.create();

        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, Phone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);

        //token过期时间     已经设置了redis中存储的过期时间，故这里可以不设置
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 1); //找到一天后的时间点
//        Date date = calendar.getTime();
//        builder.withExpiresAt(date);
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());   //防止每次刷新的token都一样

        //整合map
        map.forEach((k, v) -> builder.withClaim(k, v));

        //生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    //解析token
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString() ;

        return new TokenResult(phone, identity);
    }

    /**
     * 校验token，主要判断token是否异常
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;

        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokenResult;
    }

    public static void main(String[] args) {
        String s = generateToken("15503478318", "1", "accessToken");

        System.out.println("生成的token：" + s);
        System.out.println("==========");

        System.out.println("解析token：" + parseToken(s));

    }
}
