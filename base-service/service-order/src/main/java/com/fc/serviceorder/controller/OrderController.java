package com.fc.serviceorder.controller;

import com.fc.internalcommon.constant.HeaderParamConstants;
import com.fc.internalcommon.dto.OrderInfo;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.serviceorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    @PostMapping("/pick_up_passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {

        return orderService.pickUpPassenger(orderRequest);
    }
}
