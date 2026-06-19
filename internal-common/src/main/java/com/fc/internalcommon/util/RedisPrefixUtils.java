package com.fc.internalcommon.util;

public class RedisPrefixUtils {

    //乘客验证码的前缀
    public static String verificationCodePrefix = "verification-code-";

    //token存储的前缀
    public static String tokenPrefix = "token-";

    //设备 唯一码 前缀(用于判断黑名单设备)
    public static String blackDeviceCodePrefix = "black-device-";

    /**
     * 工具方法
     * 根据手机号生成Key
     *
     * @param phone
     * @param identity
     * @return
     */
    public static String generateKeyByPhone(String phone, String identity) {
        return verificationCodePrefix + identity + "-" + phone;
    }

    /**
     * 工具方法
     * 根据手机号和身份标识 生成token存储的key
     *
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String passengerPhone, String identity, String tokenType) {

        return tokenPrefix + passengerPhone + "-" + identity + "-" + tokenType;
    }

}
