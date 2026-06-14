package com.fc.apiboss.service;

import com.fc.apiboss.remote.ServiceDriverUserClient;
import com.fc.internalcommon.dto.DriverCarBindingRelationship;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 司机车辆 绑定
     *
     * @param relationship
     * @return
     */
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship relationship) {
        return serviceDriverUserClient.bind(relationship);
    }

    /**
     * 司机车辆 解绑
     *
     * @param relationship
     * @return
     */
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship relationship) {
        return serviceDriverUserClient.unbind(relationship);
    }
}
