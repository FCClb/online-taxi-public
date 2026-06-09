package com.fc.servicemap.remote;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 调用高德地图
 * key:
 * 7ac5f4e3d82567c118ba44c4398655c2
 */
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        //组装请求调用url
        /*
         * https://restapi.amap.com/v3/direction/driving
         * ?origin=116.481028,39.989643&destination=116.465302,40.004717
         * &extensions=all
         * &output=json
         * &key=7ac5f4e3d82567c118ba44c4398655c2
         */
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstants.DIRECTION_URL.getValue());
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude + "&destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&extensions=all");
        urlBuilder.append("&output=json");
        urlBuilder.append("&key="+amapKey);

        //调用高德接口，利用RestTemplate
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);

        //解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionEntity.getBody());

        return directionResponse;
    }

    /**
     * 解析响应体，提取出 distance和duration并放入DirectionResponse
     * @param directionString
     * @return
     */
    private DirectionResponse parseDirectionEntity(String directionString) {
        DirectionResponse directionResponse = null;
        try {
            //转换成json
            JSONObject result = JSONObject.fromObject(directionString);

            if (result.has(AmapConfigConstants.STATUS.getValue())) {    //判断响应正常
                int status = result.getInt(AmapConfigConstants.STATUS.getValue());
                if (status == 1) {  //判断响应码正确
                    if (result.has(AmapConfigConstants.ROUTE.getValue())) { //判断是否有route
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE.getValue());
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS.getValue());
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.has(AmapConfigConstants.DISTANCE.getValue())) {
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE.getValue());
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConfigConstants.DURATION.getValue())) {
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION.getValue());
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return directionResponse;
    }
}
