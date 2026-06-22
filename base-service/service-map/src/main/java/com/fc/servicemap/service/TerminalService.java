package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.internalcommon.response.TrsearchResponse;
import com.fc.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 高德地图-猎鹰轨迹服务-终端管理
 */
@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    /**
     * 新增 终端
     *
     * @param name
     * @return
     */
    public ResponseResult add(String name, String desc) {

        return terminalClient.add(name, desc);
    }

    /**
     * 周边搜索终端
     * @param center
     * @param radius
     * @return
     */
    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {

        return terminalClient.aroundsearch(center, radius);
    }

    /**
     * 查询轨迹信息（轨迹信息包括经纬度点，里程，时间等信息）
     *
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime, Long endtime) {

        return terminalClient.trsearch(tid, starttime, endtime);
    }
}
