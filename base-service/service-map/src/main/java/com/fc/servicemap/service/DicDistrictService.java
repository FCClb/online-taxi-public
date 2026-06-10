package com.fc.servicemap.service;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取行政区划
 */
@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    /**
     * 根据地区名称keyWords 请求地区字典
     * @param keyWords
     * @return
     */
    public ResponseResult initDicDistrict(String keyWords) {

        //获取结果
        String disDistrict = mapDicDistrictClient.initDicDistrict(keyWords);

        //解析结果

        //插入数据库

        return ResponseResult.success();

    }
}
