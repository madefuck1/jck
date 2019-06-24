package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.shop.ShopStatisticsDto;
import com.soufang.base.page.PageHelp;
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
    PageHelp<ShopDto> appGetList(ShopSo so);

    @RequestMapping(value = "/core/shop/getDetail", method = RequestMethod.POST)
    ShopDto getShopDetail(Long shopId);

    @RequestMapping(value = "/core/shop/updateShop",method = RequestMethod.POST)
    Result updateShop(@RequestBody ShopDto shopDto);

    @RequestMapping(value = "/core/shop/getHotShop",method = RequestMethod.POST)
    List<ShopDto> getHotShop();

    @RequestMapping(value = "/core/shop/getShopStatisticsInfo",method = RequestMethod.POST)
    ShopStatisticsDto getShopStatisticsInfo(Long shopId);

    @RequestMapping(value = "/core/shop/getShopProductManaList",method = RequestMethod.POST)
    List<ProductDto> getShopProductManaList(ProductDto productDto);
}
