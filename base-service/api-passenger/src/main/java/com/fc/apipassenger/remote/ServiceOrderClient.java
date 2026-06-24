package com.fc.apipassenger.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单服务
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/add")
    ResponseResult add(@RequestBody OrderRequest orderRequest);

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
