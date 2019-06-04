package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.AddOrderSo;
import com.soufang.base.search.order.OrderSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
@FeignClient(name = "core")
public interface AdminOrderFeign {

    @RequestMapping(value = "/core/order/getList", method = RequestMethod.POST)
    PageHelp<OrderShopDto> getList(@RequestBody OrderSo orderSo);

    @RequestMapping(value = "/core/order/saveOrderProduct", method = RequestMethod.POST)
    Result saveOrderProduct(@RequestBody AddOrderSo so);

    @RequestMapping(value = "/core/order/delOrder", method = RequestMethod.POST)
    Result delOrder(String orderNumber);
}
