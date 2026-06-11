package com.fc.servicemap.service;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.DicDistrict;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.servicemap.mapper.DicDistrictMapper;
import com.fc.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 获取行政区划
 */
@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    //todo 批量插入提升性能:改为 List 收集所有区划实体，最后批量 batchInsert

    /**
     * 根据地区名称keyWords 请求地区字典 并且插入数据库
     * @param keyWords
     * @return
     */
    public ResponseResult initDicDistrict(String keyWords) {

        if (!StringUtils.hasText(keyWords)) {
            return ResponseResult.fail("地区字典关键字不能为空");
        }

        //调用远程接口获取区划字符串
        String dicDistrict = mapDicDistrictClient.initDicDistrict(keyWords);

        //解析JSON并校验接口状态
        JSONObject disDistrictJsonObject = JSONObject.fromObject(dicDistrict);
        if (disDistrictJsonObject.getInt(AmapConfigConstants.STATUS.getValue()) != 1) { //判断响应状态码
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), disDistrictJsonObject.getString(AmapConfigConstants.STATUS.getValue()));
        }

        JSONArray districtsJsonArray = disDistrictJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS.getValue());

        //获取顶层区域数组（国家级）
        for (int i = 0; i < districtsJsonArray.size(); i++) {
            JSONObject districtsJsonObject = districtsJsonArray.getJSONObject(i);
            String addressCode = districtsJsonObject.getString(AmapConfigConstants.ADCODE.getValue());
            String name = districtsJsonObject.getString(AmapConfigConstants.NAME.getValue());
            String parentAddressCode = addressCode; //国家的父区划code为自己的区划code
            String level = districtsJsonObject.getString(AmapConfigConstants.LEVEL.getValue());

            //插入国家级区划数据
            insertDicDistrict(addressCode, name, parentAddressCode, level);

            // 递归处理当前节点的所有子区划
            JSONArray childDistricts  = districtsJsonObject.getJSONArray(AmapConfigConstants.DISTRICTS.getValue());
            recursionInsertDistrict(childDistricts, addressCode);

        }

        return ResponseResult.success("插入地区区划字典成功");

    }

    /**
     * 递归插入区划数据（核心递归方法）
     * @param childDistricts 当前层级的子区域数组
     * @param parentCode 上一级区划编码（父编码）
     */
    private void recursionInsertDistrict(JSONArray childDistricts, String parentCode) {
        // 递归终止条件：子数组为空，直接返回
        if (childDistricts == null || childDistricts.isEmpty()) {
            return;
        }

        for (int i = 0; i < childDistricts.size(); i++) {
            JSONObject district = childDistricts.getJSONObject(i);
            String addressCode = district.getString(AmapConfigConstants.ADCODE.getValue());
            String name = district.getString(AmapConfigConstants.NAME.getValue());
            String level = district.getString(AmapConfigConstants.LEVEL.getValue());

            if (level.equals(AmapConfigConstants.STREET.getValue())) {
                continue;
            } else {
                // 插入当前层级数据：父编码 = 上层传入的 parentCode
                insertDicDistrict(addressCode, name, parentCode, level);
            }

            // 继续递归：获取当前节点的下一级子区域，当前addressCode作为新的父编码
            JSONArray nextChildDistricts = district.getJSONArray(AmapConfigConstants.DISTRICTS.getValue());
            recursionInsertDistrict(nextChildDistricts, addressCode);
        }
    }

    /**
     * 插入一条区划
     * @param addressCode
     * @param name
     * @param parentAddressCode
     * @param level
     */
    private void insertDicDistrict(String addressCode, String name,String parentAddressCode,String level) {
        //数据库对象
        DicDistrict district = new DicDistrict();
        district.setAddressCode(addressCode);
        district.setAddressName(name);
        //判断父级addressCode
        district.setParentAddressCode(parentAddressCode);
        //根据级别判断level（省级，市级）
        district.setLevel(generateLevel(level));

        //插入数据库
        dicDistrictMapper.insert(district);
    }

    /**
     * 根据级别返回level等级
     * @param level
     * @return
     */
    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals("country")) {
            levelInt = 0;
        }else if (level.trim().equals("province")) {
            levelInt = 1;
        }else if (level.trim().equals("city")) {
            levelInt = 2;
        }else if (level.trim().equals("district")) {
            levelInt = 3;
        }

        return levelInt;
    }
}
