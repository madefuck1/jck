package com.soufang.feign;

import com.soufang.base.dto.shop.ShopDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "core")
public interface ShopFeign {

    @RequestMapping(value = "/core/shop/getByUserId", method = RequestMethod.POST)
    ShopDto getByUserId(@RequestBody Long userId);

}
