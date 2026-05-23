package com.fc.serviceverification.controller;

import com.fc.internalcommon.dto.ResponseResult;
import com.fc.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable int size) {

        System.out.println("size: " + size);
        //生成验证码
        double mathRandom = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
        int resultInt = (int) mathRandom;

        //定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt);



        return ResponseResult.success(response);
    }
}
