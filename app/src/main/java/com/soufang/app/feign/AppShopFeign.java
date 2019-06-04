package com.soufang.app.feign;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.shop.ShopSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface AppShopFeign {

    @RequestMapping(value = "/core/shop/getByUserId", method = RequestMethod.POST)
    ShopDto getByUserId(@RequestBody Long userId);


    @RequestMapping(value = "/core/shop/appGetList", method = RequestMethod.POST)
    List<ShopDto> appGetList(ShopSo so);

    @RequestMapping(value = "/core/shop/getDetail", method = RequestMethod.POST)
    ShopDto getShopDetail(Long shopId);
}
