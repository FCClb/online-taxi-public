package com.fc.apidriver.service;

import com.fc.apidriver.remote.ServiceDriverUserClient;
import com.fc.apidriver.remote.ServiceMapClient;
import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.ApiDriverPointRequest;
import com.fc.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高德猎鹰服务-轨迹点上传
 */
@Service
public class PointService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {

        //获取carId
        Long carId = apiDriverPointRequest.getCarId();

        //调用service-driver-user服务，通过carId获取tid和trid
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
        Car car = carById.getData();
        String tid = car.getTid();
        String trid = car.getTrid();

        //调用service-map服务，上传轨迹点
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());
        return serviceMapClient.upload(pointRequest);
    }

}
