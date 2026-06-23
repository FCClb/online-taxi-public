package com.fc.servicepay.remote;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 乘客支付完成 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/order/pay")
    ResponseResult pay(@RequestBody OrderRequest orderRequest);
}
