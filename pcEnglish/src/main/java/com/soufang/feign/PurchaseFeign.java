package com.soufang.feign;

import com.soufang.base.search.purchase.PurchaseSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface PurchaseFeign {

    //接收报价
    @RequestMapping(value = "core/purchase/acceptPurchasePc",method = RequestMethod.POST)
    int acceptPurchasePc(@RequestBody PurchaseSo purchaseSo);

    //查询当前用户是否存在报价信息
    @RequestMapping(value = "core/purchase/userPurchaseNumber",method = RequestMethod.POST)
    int userPurchaseNumber(@RequestBody PurchaseSo purchaseSo);

}
