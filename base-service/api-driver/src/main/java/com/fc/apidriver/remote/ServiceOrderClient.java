package com.fc.apidriver.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用
 * 订单服务
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 司机前往接驾乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/to-pick-up-passenger")
    ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);

    /**
     * 司机到达上车点 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/arrived-departure")
    ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest);

    /**
     * 司机接到乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/pick-up-passenger")
    ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);

    /**
     * 乘客到达目的地/行程终止 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/passenger-getoff")
    ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest);

    /**
     * 司机发起收款 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/to-start-pay")
    ResponseResult toStartPay(@RequestBody OrderRequest orderRequest);

    /**
     * 订单取消
     *
     * @param orderId
     * @param identity
     * @return
     */
    @PostMapping("/order/cancel")
    ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity);
}
