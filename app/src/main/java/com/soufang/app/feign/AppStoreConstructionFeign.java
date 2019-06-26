package com.soufang.app.feign;

import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "core")
public interface AppStoreConstructionFeign {
    // 店铺装修信息
    @RequestMapping(value = "/core/store/getStoreInfo", method = RequestMethod.POST)
    StoreConstructionDto getStoreInfo(@RequestBody Long shopId);

    @RequestMapping(value = "/core/store/isExistStoreInfo", method = RequestMethod.POST)
    Boolean isExistStoreInfo(Long shopId);

    @RequestMapping(value = "/core/store/storeAssort", method = RequestMethod.POST)
    List<StoreExclusiveAssortDto> getStoreAssort(StoreExclusiveAssortDto storeExclusiveAssortDto);
}
