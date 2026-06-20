package com.fc.servicemap.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import com.fc.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 高德地图-猎鹰轨迹服务-终端管理
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    /**
     * 新增 终端
     *
     * @param name
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestParam("name") String name, @RequestParam("desc") String desc) {

        return terminalService.add(name, desc);
    }


    /**
     * 周边搜索终端
     *
     * @param center
     * @param radius
     * @return
     */
    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundsearch(@RequestParam String center, @RequestParam Integer radius) {

        return terminalService.aroundsearch(center, radius);
    }

}
