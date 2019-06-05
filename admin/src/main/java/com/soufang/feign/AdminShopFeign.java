package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("core")
public interface AdminShopFeign {

    @RequestMapping(value = "/core/shop/getList",method = RequestMethod.POST)
    PageHelp<ShopDto> getList(@RequestBody ShopSo shopSo);

    @RequestMapping(value = "/core/shop/getDetail",method = RequestMethod.POST)
    ShopDto getDetail(@RequestBody Long id);

    @RequestMapping(value = "/core/shop/getUser",method = RequestMethod.POST)
    UserDto getUser(@RequestBody Long id);

    @RequestMapping(value = "/core/shop/reviewYes",method = RequestMethod.POST)
    Result reviewYes(@RequestBody Long id);

    @RequestMapping(value = "/core/shop/reviewNo",method = RequestMethod.POST)
    Result reviewNo(@RequestBody ShopDto shopDto);

    @RequestMapping(value ="/core/shop/admin/createShop")
    Result adminCreateShop(@RequestBody UserDto userDto);
}
