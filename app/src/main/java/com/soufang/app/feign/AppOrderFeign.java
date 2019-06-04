package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: chen
 * @Date: 2019/5/21
 * @Description:
 */
@FeignClient("core")
public interface AppOrderFeign {

    @RequestMapping(value = "/core/order/getList", method = RequestMethod.POST)
    PageHelp<OrderShopDto> getList(@RequestBody OrderSo orderSo);

    @RequestMapping(value = "/core/order/createOrder", method = RequestMethod.POST)
    Result createOrder(@RequestBody OrderDto orderDto);

    @RequestMapping(value = "/core/order/getDetail", method = RequestMethod.POST)
    OrderShopDto getDetail(@RequestBody Long orderShopId);

    @RequestMapping(value = "/core/order/updateOrderStatus", method = RequestMethod.POST)
    Result updateProductStatus(OrderShopDto orderShopDto);

}
