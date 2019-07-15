package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.contract.ContractDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.pay.PayDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface OrderFeign {

    @RequestMapping(value = "/core/order/getList", method = RequestMethod.POST)
    PageHelp<OrderShopDto> getList(@RequestBody OrderSo orderSo);

    @RequestMapping(value = "/core/order/createOrder", method = RequestMethod.POST)
    Result createOrder(@RequestBody OrderDto orderDto);

    @RequestMapping(value = "/core/order/getDetail", method = RequestMethod.POST)
    OrderShopDto getDetail(@RequestBody Long orderShopId);

    @RequestMapping(value = "/core/order/updateOrderStatus", method = RequestMethod.POST)
    Result updateOrderStatus(@RequestBody OrderShopDto orderShopDto);

    @RequestMapping(value = "/core/order/payOrder", method = RequestMethod.POST)
    Result payOrder(@RequestBody OrderShopDto orderShopDto);

    @RequestMapping(value = "/core/pay/createPay", method = RequestMethod.POST)
    PayDto createPay(@RequestBody PayDto payDto);

    //判断是否已经有合同
    @RequestMapping(value = "/core/contract/getDetail" ,method = RequestMethod.POST)
    ContractDto getDetail(@RequestBody ContractDto contractDto);

    @RequestMapping(value = "/core/contract/down", method = RequestMethod.POST)
    Result down(@RequestBody ContractDto contractDto);

    @RequestMapping(value = "/core/contract/upload", method = RequestMethod.POST)
    Result upload(@RequestBody ContractDto contractDto);

    @RequestMapping(value = "/core/order/paySuccess", method = RequestMethod.POST)
    Result paySuccess(@RequestBody PayDto payDto);

    @RequestMapping(value = "/core/pay/getByPayNumber", method = RequestMethod.POST)
    PayDto getByPayNumber(@RequestBody String payNumber);
}
