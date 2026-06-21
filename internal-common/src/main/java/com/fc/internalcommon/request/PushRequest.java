package com.fc.internalcommon.request;

import lombok.Data;

/**
 * 发送消息 请求体
 */
@Data
public class PushRequest {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户身份标识
     */
    private String identity;

    /**
     * 消息内容
     */
    private String content;

}
