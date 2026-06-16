package com.fc.servicemap.remote;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.ServiceResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用
 * 高德地图-猎鹰轨迹服务-服务管理控制器
 */
@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name) {
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.SERVICE_ADD_URL.getValue());
        url.append("?");
        url.append("key=" + amapKey);
        url.append("&name=" + name);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null, String.class);

        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);

        return ResponseResult.success(serviceResponse);
    }
}
