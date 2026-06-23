package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceOrderClient;
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
    private ServiceOrderClient orderClient;

    /**
     * 司机前往接驾乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        return orderClient.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机到达上车点 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult arrivedDeparture(OrderRequest orderRequest) {
        return orderClient.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        return orderClient.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地/行程终止 修改订单状态
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult passengerGetoff(OrderRequest orderRequest) {
        return orderClient.passengerGetoff(orderRequest);
    }

}
