package com.fc.serviceorder.remote;

import com.fc.internalcommon.dto.PriceRule;
import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    /**
     * 查询 最新的计价规则
     *
     * @param fareType
     * @return
     */
    @GetMapping("/price-rule/get-newest-version")
    ResponseResult<PriceRule> getNewestVersion(@RequestParam String fareType);

    /**
     * 判断 计价规则是否最新
     *
     * @param fareType
     * @return
     */
    @GetMapping("/price-rule/is-new")
    ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);

}
