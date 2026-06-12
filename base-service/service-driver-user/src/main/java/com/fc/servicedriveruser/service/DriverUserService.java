package com.fc.servicedriveruser.service;

import com.fc.servicedriveruser.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverMapper driverMapper;


}
