package com.fc.serviceprice.controller;

import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计价规则 管理
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;

    /**
     * 新增 计价规则
     * @param priceRule
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {

        return priceRuleService.add(priceRule);
    }

    /**
     * 更新 计价规则
     * @param priceRule
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {

        return priceRuleService.edit(priceRule);
    }

}
