package com.fc.internalcommon.util;

public class RedisPrefixUtils {

    //乘客验证码的前缀
    public static String verificationCodePrefix = "passenger-verification-code-";

    //token存储的前缀
    public static String tokenPrefix = "token-";

    /**
     * 工具方法
     * 根据手机号生成Key
     * @param passengerPhone
     * @return
     */
    public static String generateKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 工具方法
     * 根据手机号和身份标识 生成token存储的key
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String passengerPhone, String identity) {
        return tokenPrefix + passengerPhone + "-" + identity;
    }

}
