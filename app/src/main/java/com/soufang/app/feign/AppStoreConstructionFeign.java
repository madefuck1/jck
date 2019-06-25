package com.soufang.app.feign;

import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "core")
public interface AppStoreConstructionFeign {
    // 店铺装修信息
    @RequestMapping(value = "/core/store/getStoreInfo", method = RequestMethod.POST)
    StoreConstructionDto getStoreInfo(@RequestBody Long shopId);

    @RequestMapping(value = "/core/store/isExitStoreInfo", method = RequestMethod.POST)
    Boolean isExitStoreInfo(Long shopId);
}
