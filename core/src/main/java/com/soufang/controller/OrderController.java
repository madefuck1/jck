package com.soufang.controller;

import com.soufang.base.BusinessException;
import com.soufang.base.Result;
import com.soufang.base.dto.contract.ConfirmContractDto;
import com.soufang.base.dto.contract.ContractDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.pay.PayDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.AddOrderSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.service.ContractService;
import com.soufang.service.OrderService;
import com.soufang.service.PayService;
import com.soufang.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/22
 * @Description:
 */
@RestController
@RequestMapping("/core/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    SysParamService sysParamService;

    @Autowired
    ContractService contractService;

    @Autowired
    PayService payService;

    /**
     * 买家生成订单
     *
     * @param orderDto
     * @return
     */
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public Result createOrder(@RequestBody OrderDto orderDto) {
        Result result = new Result();
        try {
            orderService.createOrder(orderDto, sysParamService.getOrderNumber());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    //去支付，订单重新生成
    @RequestMapping(value = "payOrder", method = RequestMethod.POST)
    public Result payOrder(@RequestBody OrderShopDto orderShopDto) {
        Result result = new Result();
        try {
            orderService.payOrder(orderShopDto, sysParamService.getOrderNumber());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 用户提交合同
     *
     * @param contractDto
     * @return
     */
    @RequestMapping(value = "submitContract", method = RequestMethod.POST)
    public Result submitContract(@RequestBody ContractDto contractDto) {
        Result result = new Result();
        try {
            contractService.createContract(contractDto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 平台确认订单合同
     *
     * @param confirmContractDto
     * @return
     */
    @RequestMapping(value = "confirmOrder", method = RequestMethod.POST)
    public Result confirmOrder(@RequestBody ConfirmContractDto confirmContractDto) {
        Result result = new Result();
        try {
            orderService.confirmOrder(confirmContractDto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取订单列表
     *
     * @param orderSo
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public PageHelp<OrderShopDto> getList(@RequestBody OrderSo orderSo) {
        PageHelp<OrderShopDto> pageHelp = new PageHelp<>();
        List<OrderShopDto> list = orderService.getList(orderSo);
        for (OrderShopDto orderShopDto : list) {
            orderShopDto.setStatusMessage(OrderStatusEnum.getByKey(orderShopDto.getOrderShopStatus()).getMessage());
            orderShopDto.setStatusColor(OrderStatusEnum.getByKey(orderShopDto.getOrderShopStatus()).getButtonColor());
            List<OrderProductDto> orderProductDtoList = new ArrayList<>();
            for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
                orderProductDto.setProductImage(orderProductDto.getProductImage());
                orderProductDtoList.add(orderProductDto);
            }
            orderShopDto.setOrderProducts(orderProductDtoList);
        }
        int count = orderService.getCount(orderSo);
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    /**
     * 通过订单号获取订单明细
     *
     * @param orderShopId
     * @return
     */
    @RequestMapping(value = "getDetail", method = RequestMethod.POST)
    public OrderShopDto getDetail(@RequestBody Long orderShopId) {
        OrderShopDto orderShopDto = orderService.getDetail(orderShopId);
        orderShopDto.setOrderDto(orderService.getOrder(orderShopDto.getOrderNumber()));
        List<OrderProductDto> orderProductDtoList = new ArrayList<>();
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
            orderProductDtoList.add(orderProductDto);
        }
        orderShopDto.setOrderProducts(orderProductDtoList);
        return orderShopDto;
    }

    @RequestMapping(value = "saveOrderProduct", method = RequestMethod.POST)
    public Result saveOrderProduct(@RequestBody AddOrderSo so) {
        Result result = new Result();
        try {
            orderService.saveOrderProduct(so, sysParamService.getOrderNumber());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "paySuccess", method = RequestMethod.POST)
    public Result paySuccess(@RequestBody PayDto payDto) {
        Result result = new Result();
        try {
            orderService.paySuccess(payDto.getOrderNumber());
            payService.paySuccess(payDto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 卖家修改价格
     *
     * @param orderShopDto
     * @return
     */
    @RequestMapping(value = "updateOrder", method = RequestMethod.POST)
    public Result updateOrder(@RequestBody OrderShopDto orderShopDto) {
        Result result = new Result();
        try {
            orderService.updateOrder(orderShopDto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 取消订单、收货、删除订单（逻辑删）
     *
     * @param orderShopDto
     * @return
     */
    @RequestMapping(value = "updateOrderStatus", method = RequestMethod.POST)
    public Result updateOrderStatus(@RequestBody OrderShopDto orderShopDto) {
        Result result = orderService.updateOrderStatus(orderShopDto);
        return result;
    }

    //卖家修改价格
    @RequestMapping(value = "updateActualPrice",method = RequestMethod.POST)
    Result updateActualPrice(@RequestBody OrderShopDto orderShopDto){
        Result result = orderService.updateActualPrice(orderShopDto);
        return result;
    }

    //发货
    @RequestMapping(value = "sendProduct", method = RequestMethod.POST)
    Result sendProduct(@RequestBody OrderShopDto orderShopDto){
        return orderService.sendProduct(orderShopDto);
    }
//    /**
//     * 删除订单（逻辑删）
//     *
//     * @param orderShopDto
//     * @return
//     */
//    @RequestMapping(value = "delOrder", method = RequestMethod.POST)
//    public Result delOrder(@RequestBody OrderShopDto orderShopDto) {
//        Result result = orderService.updateOrderStatus(orderShopDto);
//        return result;
//    }


}
