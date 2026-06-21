package com.fc.serviceorder.remote;

import com.fc.internalcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 消息推送服务
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    /**
     * 发送消息
     *
     * @param pushRequest
     * @return
     */
    @PostMapping("/push")
    String push(@RequestBody PushRequest pushRequest);
}
