package com.fc.servicemap.remote;

import com.fc.internalcommon.constant.AmapConfigConstants;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.TerminalResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程调用
 * 高德地图-猎鹰轨迹服务-终端管理
 */
@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新增 终端
     *
     * @param name
     * @return
     */
    public ResponseResult add(String name, String desc) {

        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD_URL.getValue());
        url.append("?");
        url.append("key=" + amapKey);
        url.append("&sid=" + amapSid);
        url.append("&name=" + name);
        url.append("&desc=" + desc);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String tid = data.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    /**
     * 周边搜索终端
     * @param center
     * @param radius
     * @return
     */
    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_AROUNDSEARCH_URL.getValue());
        url.append("?");
        url.append("key=" + amapKey);
        url.append("&sid=" + amapSid);
        url.append("&center=" + center);
        url.append("&radius=" + radius);

        ResponseEntity<String> StringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);

        String body = StringResponseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");

        List<TerminalResponse> list = new ArrayList<TerminalResponse>();

        JSONArray results = data.getJSONArray("results");
        for (int i = 0; i < results.size(); i++) {
            TerminalResponse terminalResponse = new TerminalResponse();

            JSONObject object = results.getJSONObject(i);
            String tid = object.getString("tid");
            //这一步可能会错误赋值，需要先getString()，再Long.parseLong()，是net.sf.json 缺陷
            Long carId = Long.parseLong(object.getString("desc"));

            terminalResponse.setTid(tid);
            terminalResponse.setCarId(carId);
            list.add(terminalResponse);
        }


        return ResponseResult.success(list);
    }

    //forTest
    //net.sf.json 缺陷
    //未来开发解析JSON建议直接用Spring/Spring Boot 默认内置的Jackson
    public static void main(String[] args) {
        String numStr = "2067182759027159041";
        // 模拟 getLong 的内部逻辑
        long wrong = Double.valueOf(numStr).longValue();
        // 正确写法
        long correct = Long.parseLong(numStr);

        System.out.println("getLong 效果：" + wrong);   // 输出 2067182759027159040
        System.out.println("parseLong 效果：" + correct); // 输出 2067182759027159041
    }

}
