package com.fc.servicemap.remote;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.dto.TraceResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用
 * 高德地图-猎鹰轨迹服务-轨迹管理
 */
@Service
public class TraceClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TraceResponse> add(String tid) {
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TRACE_ADD_URL.getValue());
        url.append("?key=").append(amapKey).append("&sid=").append(amapSid).append("&tid=").append(tid);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        //轨迹id
        String trid = data.getString("trid");
        //轨迹名称
        String trname = "";
        if (data.has("trname")) {
            trname = data.getString("trname");
        }

        TraceResponse traceResponse = new TraceResponse();
        traceResponse.setTrid(trid);
        traceResponse.setTrname(trname);
        return ResponseResult.success(traceResponse);
    }

}
