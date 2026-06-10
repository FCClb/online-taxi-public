package com.fc.servicemap.service;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
        JSONObject disDistrictJsonObject = JSONObject.fromObject(disDistrict);
        if (disDistrictJsonObject.getInt(AmapConfigConstants.STATUS.getValue()) != 1) { //响应错误
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), disDistrictJsonObject.getString(AmapConfigConstants.STATUS.getValue()));
        }
        JSONArray districtsJsonArray = disDistrictJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS.getValue());

        for (int i = 0; i < districtsJsonArray.size(); i++) {
            JSONObject districtsJsonObject = districtsJsonArray.getJSONObject(i);
            String addressCode = districtsJsonObject.getString(AmapConfigConstants.ADCODE.getValue());
            String name = districtsJsonObject.getString(AmapConfigConstants.NAME.getValue());
            String parentAddressCode = "0";
            districtsJsonObject.getString(AmapConfigConstants.LEVEL.getValue());

        }

        //插入数据库

        return ResponseResult.success();

    }
}
