package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/13
 * @Description:
 */
@FeignClient(value = "core")
public interface ShopCarFeign {

    @RequestMapping(value = "/core/shopCar/getShopCarList", method = RequestMethod.POST)
    PageHelp<ShopCarDto> getShopCarList(@RequestBody Long userId);

    @RequestMapping(value = "/core/shopCar/delShopCar", method = RequestMethod.POST)
    Result delete(@RequestBody ShopCarDto dto);

    @RequestMapping(value = "/core/shopCar/addToShopCar", method = RequestMethod.POST)
    Result addToShopCar(@RequestBody ShopCarDto dto);

    @RequestMapping(value = "/core/shopCar/getShopCarCountByUserId", method = RequestMethod.POST)
    int getShopCarCountByUserId(@RequestBody Long userId);

    @RequestMapping(value = "/core/shopCar/getShopCarDtoById", method = RequestMethod.POST)
    ShopCarDto getShopCarDtoById(@RequestBody Long shopCarId);

    @RequestMapping(value = "/core/shopCar/getShopCarProductDtoById", method = RequestMethod.POST)
    ShopCarProductDto getShopCarProductDtoById(@RequestBody Long shopCarProductId);

    @RequestMapping(value = "/core/shopCar/editShopCar", method = RequestMethod.POST)
    Result update(ShopCarProductDto shopCarProductDto);

    @RequestMapping(value = "/core/productManage/selectPrice", method = RequestMethod.POST)
    BigDecimal selectPrice(@RequestBody ShopCarProductDto shopCarProductDto);

    @RequestMapping(value = "/core/shopCar/delShopCarProduct", method = RequestMethod.POST)
    Result delShopCar(List<ShopCarDto> shopCarDtoList);
}

