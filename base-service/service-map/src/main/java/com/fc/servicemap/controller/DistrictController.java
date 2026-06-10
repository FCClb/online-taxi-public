package com.fc.servicemap.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取行政区划
 */
@RestController
public class DistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    /**
     * 请求地区字典
     * @return
     */
    @GetMapping("/dic-district")
    public ResponseResult initDistrict(String keyWords) {

        return dicDistrictService.initDicDistrict(keyWords);

    }


}
