package com.fc.apidriver.controller;

import com.fc.apidriver.service.OrderService;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 司机前往接驾乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderService.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机到达上车点 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {
        return orderService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地/行程终止 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest) {
        return orderService.passengerGetoff(orderRequest);
    }

    /**
     * 订单取消
     *
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId) {

        return orderService.cancel(orderId);
    }

}
