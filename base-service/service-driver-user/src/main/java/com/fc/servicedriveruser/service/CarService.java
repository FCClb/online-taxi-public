package com.fc.servicedriveruser.service;

import com.fc.internalcommon.dto.Car;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TraceResponse;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.servicedriveruser.mapper.CarMapper;
import com.fc.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 新增 车辆
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //获得此车辆 对应的 终端tid
        ResponseResult<TerminalResponse> terminalResponseResult = serviceMapClient.addTerminal(car.getVehicleNo());
        TerminalResponse terminalData = terminalResponseResult.getData();
        String tid = terminalData.getTid();
        car.setTid(tid);

        //获得此车辆 对应的 轨迹trid
        ResponseResult<TraceResponse> traceResponseResponseResult = serviceMapClient.addTrace(tid);
        TraceResponse traceData = traceResponseResponseResult.getData();
        String trid = traceData.getTrid();
        String trname = traceData.getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.insert(car);

        return ResponseResult.success("新增车辆成功");
    }
}
