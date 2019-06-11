package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.storeConstruction.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "core")
public interface StoreConstructionFeign {
    // 店铺装修信息
    @RequestMapping(value = "/core/store/getStoreInfo", method = RequestMethod.POST)
    StoreConstructionDto getStoreInfo(@RequestBody Long shopId);

    // 注册店铺装修信息
    @RequestMapping(value = "/core/store/register", method = RequestMethod.POST)
    Result register(@RequestBody StoreConstructionDto dto);

    //更新店铺装修信息
    @RequestMapping(value = "/core/store/update", method = RequestMethod.POST)
    Result update(@RequestBody StoreConstructionDto dto);

    @RequestMapping(value = "/core/store/storeAssort", method = RequestMethod.POST)
    List<StoreExclusiveAssortDto> getStoreAssort(@RequestBody StoreExclusiveAssortDto dto);

    @RequestMapping(value = "/core/store/searchProduct", method = RequestMethod.POST)
    List<StoreProductAssortDto> searchProduct(@RequestBody StoreProductAssortDto storeProductAssortDto);

    @RequestMapping(value = "/core/store/delAllChart", method = RequestMethod.POST)
    Result delAllChart(@RequestBody Long storeConstructionId);

    @RequestMapping(value = "/core/store/saveChart", method = RequestMethod.POST)
    Result saveChart(@RequestBody StoreCurouselMapList list);

    @RequestMapping(value = "/core/store/updExclusiveAssort", method = RequestMethod.POST)
    Result updExclusiveAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto);

    @RequestMapping(value = "/core/store/saveProductSort", method = RequestMethod.POST)
    Result saveProductSort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto);

    @RequestMapping(value = "/core/store/publish", method = RequestMethod.POST)
    Result publish(Long shopId);

    @RequestMapping(value = "/core/store/delAssortByKey", method = RequestMethod.POST)
    Result delAssortByKey(@RequestBody Long assortId);

    @RequestMapping(value = "/core/store/registerAssort", method = RequestMethod.POST)
    Result registerAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto);

    @RequestMapping(value = "/core/store/updAssort", method = RequestMethod.POST)
    Result updAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto);
}
