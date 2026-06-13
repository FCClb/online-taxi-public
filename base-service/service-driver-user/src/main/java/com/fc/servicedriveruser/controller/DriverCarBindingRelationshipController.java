package com.fc.servicedriveruser.controller;

import com.fc.internalcommon.dto.DriverCarBindingRelationship;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    /**
     * 司机车辆 绑定
     * @param relationship
     * @return
     */
    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship relationship) {

        return driverCarBindingRelationshipService.bind(relationship);
    }

    /**
     * 司机车辆 解绑
     * @param relationship
     * @return
     */
    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship relationship) {

        return driverCarBindingRelationshipService.unbind(relationship);
    }

}
