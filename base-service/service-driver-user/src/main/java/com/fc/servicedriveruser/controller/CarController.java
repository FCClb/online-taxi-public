package com.fc.servicedriveruser.controller;

import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * 新增 车辆
     * @param car
     * @return
     */
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {

        return carService.addCar(car);
    }

    /**
     * 查询 根据id查询车辆
     *
     * @param carId
     * @return
     */
    @GetMapping("/car")
    public ResponseResult getCarById(@RequestParam Long carId) {

        return carService.getCarById(carId);
    }
}
