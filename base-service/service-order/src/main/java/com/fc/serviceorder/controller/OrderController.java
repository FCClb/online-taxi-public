package com.fc.serviceorder.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {

        return orderService.add(orderRequest);
    }

    /**
     * 司机前往接驾乘客 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {

        return orderService.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机到达上车点 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {

        return orderService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {

        return orderService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地/行程终止 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {

        return orderService.passengerGetoff(orderRequest);
    }

    /**
     * 司机发起收款 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-start-pay")
    public ResponseResult toStartPay(@RequestBody OrderRequest orderRequest) {

        return orderService.toStartPay(orderRequest);
    }

    /**
     * 乘客支付完成 修改订单状态
     * @param orderRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest) {

        return orderService.pay(orderRequest);
    }

    /**
     * 订单取消
     * @param orderId
     * @param identity
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(Long orderId, String identity) {

        return orderService.cancel(orderId, identity);
    }
}
