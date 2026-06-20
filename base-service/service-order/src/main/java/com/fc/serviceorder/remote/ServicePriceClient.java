package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    /**
     * 判断 计价规则是否最新
     *
     * @param fareType
     * @return
     */
    @GetMapping("/price-rule/is-new")
    ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);


    /**
     * 根据城市编码和车型查询计价规则 是否存在
     *
     * @param priceRule
     * @return
     */
    @PostMapping("/price-rule/if-exists")
    ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule);
}
