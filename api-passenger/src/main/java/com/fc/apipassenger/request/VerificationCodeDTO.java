package com.fc.apipassenger.request;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
