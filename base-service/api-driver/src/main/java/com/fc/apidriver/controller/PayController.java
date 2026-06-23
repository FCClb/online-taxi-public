package com.fc.apidriver.controller;

import com.fc.apidriver.service.PayService;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付管理
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;
    /**
     * 司机发起收款/向乘客发收款消息
     *
     * @param orderId
     * @param price
     * @return
     */
    @PostMapping("/push-pay-info")
    public ResponseResult pushPayInfo(@RequestParam String orderId, @RequestParam String price, @RequestParam Long passengerId) {

        return payService.pushPayInfo(orderId, price, passengerId);
    }
}
