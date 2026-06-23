package com.fc.servicepay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.fc.internalcommon.request.OrderRequest;
import com.fc.servicepay.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付管理
 */
@Controller
@RequestMapping("/alipay")
@ResponseBody
@Slf4j
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    /*
    http://localhost:9001/alipay/pay?subject=车费1&outTradeno=1003&totalAmount=103
     */

    @GetMapping("/pay")
    public String pay(String subject, String outTradeno, String totalAmount) {
        AlipayTradePagePayResponse response = null;
        try {
            response = Factory.Payment.Page().pay(subject, outTradeno, totalAmount, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {
        String tradeStatus = request.getParameter("trade_status");
        if (tradeStatus.trim().equals("TRADE_SUCCESS")) {
            HashMap<String, String> param = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String name : parameterMap.keySet()) {
                param.put(name, request.getParameter(name));
            }

            //验证身份
            if (Factory.Payment.Common().verifyNotify(param)) {
                log.info("通过支付宝的验证");

                Long orderId = Long.parseLong(param.get("out_trade_no"));
                alipayService.pay(orderId);

            } else {
                log.info("支付宝验证 不通过！");
            }
        }

        return "success";
    }

}
