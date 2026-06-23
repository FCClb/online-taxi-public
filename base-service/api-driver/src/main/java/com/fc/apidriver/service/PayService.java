package com.fc.apidriver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fc.apidriver.remote.ServiceSsePushClient;
import com.fc.internalcommon.constant.IdentityEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.PushRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付管理
 */
@Service
public class PayService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceSsePushClient pushClient;

    /**
     * 司机发起收款/向乘客发收款消息
     *
     * @param orderId
     * @param price
     * @return
     */
    public ResponseResult pushPayInfo(String orderId, String price, Long passengerId) {
        //封装消息
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("price", price);
        objectNode.put("orderId", orderId);

        //推送消息
        PushRequest pushRequest = new PushRequest();
        pushRequest.setUserId(passengerId);
        pushRequest.setIdentity(IdentityEnum.PASSENGER_IDENTITY.getValue());
        pushRequest.setContent(objectNode.toString());
        pushClient.push(pushRequest);

        return ResponseResult.success("司机发起收款/向乘客发收款消息");
    }
}
