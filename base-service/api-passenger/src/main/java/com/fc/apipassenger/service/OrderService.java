package com.fc.apipassenger.service;

import com.fc.apipassenger.remote.ServiceOrderClient;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单管理
 */
@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    /**
     * 创建订单/下单
     *
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest) {

        return serviceOrderClient.add(orderRequest);
    }
}
