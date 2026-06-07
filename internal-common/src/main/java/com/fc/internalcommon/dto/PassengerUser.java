package com.fc.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerUser {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 创建用户时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改用户时间
     */
    private LocalDateTime gmtModified;

    /**
     * 用户手机号
     */
    private String passengerPhone;

    /**
     * 用户称谓
     */
    private String passengerName;

    /**
     * 用户性别（0女，1男）
     */
    private byte passengerGender;

    /**
     * 用户状态（0启用，1停用）
     */
    private byte state;

    /**
     * 用户头像
     */
    private String profilePhoto;

}
