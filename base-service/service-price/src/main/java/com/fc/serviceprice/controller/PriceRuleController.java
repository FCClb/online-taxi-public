package com.fc.serviceprice.controller;

import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import com.fc.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询 最新的计价规则
     *
     * @param fareType
     * @return
     */
    @GetMapping("/get-newest-version")
    public ResponseResult<PriceRule> getNewestVersion(@RequestParam String fareType) {

        return priceRuleService.getNewestVersion(fareType);
    }

    /**
     * 判断 计价规则是否最新
     *
     * @param fareType
     * @return
     */
    @GetMapping("/is-new")
    public ResponseResult isNew(@RequestParam String fareType, @RequestParam Integer fareVersion) {

        return priceRuleService.isNew(fareType, fareVersion);
    }

    /**
     * 根据城市编码和车型查询计价规则 是否存在
     *
     * @param priceRule
     * @return
     */
    @GetMapping("/if-exists")
    public ResponseResult ifExists(@RequestBody PriceRule priceRule) {

        return priceRuleService.ifExists(priceRule);
    }

}
