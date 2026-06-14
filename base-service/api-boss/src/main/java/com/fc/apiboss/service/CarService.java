package com.fc.apiboss.service;

import com.fc.apiboss.remote.ServiceDriverUserClient;
import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 新增 车辆
     *
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }
}
