package com.fc.serviceorder.controller;

import com.fc.internalcommon.dto.OrderInfo;
import com.fc.serviceorder.mapper.OrderMapper;
import com.fc.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-order test";
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 测试 实时订单派单
     * @param orderId
     * @return
     */
    @GetMapping("/test-real-time-order")
    public String dispatchRealTimeOrder(@RequestParam String orderId) {

        OrderInfo orderInfo = orderMapper.selectById(orderId);
        orderService.dispatchRealTimeOrder(orderInfo);

        return "test-real-time-order success";
    }

}
