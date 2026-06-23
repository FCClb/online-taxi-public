package com.fc.servicepay.service;

import com.fc.internalcommon.request.OrderRequest;
import com.fc.servicepay.remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {

    @Autowired
    private ServiceOrderClient orderClient;
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public void pay(Long orderId) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);
        serviceOrderClient.pay(orderRequest);
    }
}
