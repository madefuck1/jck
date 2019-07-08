package com.soufang.feign;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "core")
public interface ShopFeign {

    @RequestMapping(value = "/core/shop/getByUserId", method = RequestMethod.POST)
    ShopDto getByUserId(@RequestBody Long userId);

    @RequestMapping(value = "/core/shop/appGetList",method = RequestMethod.POST)
    PageHelp<ShopDto> appGetList(@RequestBody ShopSo shopSo);

    @RequestMapping(value = "/core/shop/getDetail",method = RequestMethod.POST)
    ShopDto getShopInfo(@RequestBody Long shopId);
}
