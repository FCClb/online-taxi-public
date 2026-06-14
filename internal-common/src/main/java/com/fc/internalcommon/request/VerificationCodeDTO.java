package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

    private String driverPhone;


}
