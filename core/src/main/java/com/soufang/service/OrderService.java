package com.soufang.service;

import com.soufang.base.Result;
import com.soufang.base.dto.contract.ConfirmContractDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.search.order.AddOrderSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.model.Order;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
public interface OrderService {

    List<OrderShopDto> getList(OrderSo orderSo);

    int getCount(OrderSo orderSo);

    void saveOrderProduct(AddOrderSo so, String orderNumber);

    void delOrder(Order order);

    void createOrder(OrderDto dto, String orderNumber);

    void payOrder(OrderShopDto orderShopDto , String orderNumber);
    /**
     * orderShopId , actualMoney
     * @param orderShopDto
     * @return
     */
    void updateOrder(OrderShopDto orderShopDto);

    void confirmOrder(ConfirmContractDto confirmContractDto);

    OrderShopDto getDetail(Long orderShopId);

    OrderDto getOrder(String orderNumber);

    void paySuccess(String orderNumber);

    List<OrderShopDto> getUserOrderShopList (Long userId);

    OrderDto getOrderDto (String orderNumber);

    List<OrderProductDto> getOrderProductList(Long orderShopId);

    ProductDto getProductById(Long productId);

    Result updateOrderStatus(OrderShopDto orderShopDto);

    Result updateActualPrice(OrderShopDto orderShopDto);

    Result sendProduct(OrderShopDto orderShopDto);
}
