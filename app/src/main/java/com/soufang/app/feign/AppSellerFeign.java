package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "core")
public interface AppSellerFeign {

    @RequestMapping(value = "/core/order/updateActualPrice",method = RequestMethod.POST)
    Result updateActualPrice(@RequestBody OrderShopDto orderShopDto);

    @RequestMapping(value = "/core/order/getList", method = RequestMethod.POST)
    PageHelp<OrderShopDto> getList(@RequestBody OrderSo orderSo);

    @RequestMapping(value = "/core/order/getDetail", method = RequestMethod.POST)
    OrderShopDto getDetail(@RequestBody Long orderShopId);

    @RequestMapping(value = "/core/order/sendProduct", method = RequestMethod.POST)
    Result sendProduct(@RequestBody OrderShopDto orderShopDto);

    @RequestMapping(value = "/core/order/getSaleOrderList", method = RequestMethod.POST)
    PageHelp<OrderShopDto> getSaleOrderList(@RequestBody OrderSo orderSo);
}
