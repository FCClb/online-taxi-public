package com.fc.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

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

    //生成token
    public static String generateToken(Map<String, String> map) {
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); //找到一天后的时间点
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map
        map.forEach(
            (k, v) -> {
                builder.withClaim(k, v);
            }
        );

        //整合过期时间
        builder.withExpiresAt(date);

        //生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }


    //解析token

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "fufu");
        map.put("age", "18");

        String s = generateToken(map);
        map.entrySet().forEach(System.out::println);
        System.out.println("生成的token：" + s);
    }
}
