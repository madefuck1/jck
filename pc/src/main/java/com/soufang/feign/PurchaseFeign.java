package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.vo.purchase.PurchseUseRefusedVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient("core")
public interface PurchaseFeign {

    @RequestMapping(value = "core/enquiryProduct/isUseRefused",method = RequestMethod.POST)
    Result isUseRefused(@RequestBody PurchseUseRefusedVo purchseUseRefusedVo);
}
