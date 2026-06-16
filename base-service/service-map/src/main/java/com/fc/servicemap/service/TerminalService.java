package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高德地图-猎鹰轨迹服务-终端管理
 */
@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    /**
     * 新增 终端
     * @param name
     * @return
     */
    public ResponseResult add(String name) {

        return terminalClient.add(name);
    }
}
