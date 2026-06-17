package com.fc.servicemap.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.constant.CommonStatusEnum;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 远程调用
 * 高德地图-猎鹰轨迹服务-轨迹点上传
 */
@Service
public class PointClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public ResponseResult upload(PointRequest pointRequest) {
        StringBuilder url = new StringBuilder();
        try {
            url.append(AmapConfigConstants.POINT_UPLOAD_URL.getValue());
            url.append("?key=").append(amapKey).append("&sid=").append(amapSid);
            url.append("&tid=").append(pointRequest.getTid());
            url.append("&trid=").append(pointRequest.getTrid());

            // 1. 将坐标数组序列化为标准JSON数组字符串
            String pointsJson = objectMapper.writeValueAsString(pointRequest.getPoints());
            // 2. 对JSON做URL编码
            String pointsEncoded = URLEncoder.encode(pointsJson, StandardCharsets.UTF_8.name());
            // 3. 拼接到URL参数中
            url.append("&points=").append(pointsEncoded);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.fail(CommonStatusEnum.URL_ERROR.getCode(), CommonStatusEnum.URL_ERROR.getValue());
        }

        System.out.println("上传轨迹点最终请求URL：" + url);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);

        System.out.println("上传轨迹点高德地图响应：" + forEntity.getBody());

        return ResponseResult.success(pointRequest);
    }

}
