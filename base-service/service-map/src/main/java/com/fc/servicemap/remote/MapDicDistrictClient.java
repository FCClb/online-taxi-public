package com.fc.servicemap.remote;

import com.fc.internalcommon.constant.AmapConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 调用第三方（高德地图）
 */
@Service
public class MapDicDistrictClient {

    @Value("${amap.key}")
    private String amapkey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过第三方api获取行政区划
     * https://restapi.amap.com/v3/config/district?keywords=%E5%8C%97%E4%BA%AC&subdistrict=2&key=7ac5f4e3d82567c118ba44c4398655c2
     */
    public String initDicDistrict(String keywords) {
        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords=" + keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=" + amapkey);

        //获取结果
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);

        return forEntity.getBody();
    }

}
