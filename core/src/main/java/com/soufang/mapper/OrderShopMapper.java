package com.soufang.mapper;

import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.search.order.OrderSo;
import com.soufang.model.OrderShop;

import java.util.List;

public interface OrderShopMapper {
    int deleteByPrimaryKey(Long orderShopId);

    int insert(OrderShop record);

    int insertSelective(OrderShop record);

    OrderShop selectByPrimaryKey(Long orderShopId);

    int updateByPrimaryKeySelective(OrderShop record);

    int updateByPrimaryKey(OrderShop record);

    List<OrderShop> getList(OrderSo orderSo);

    int getCount(OrderSo orderSo);

    List<OrderShop> getUserOrderShopList (Long userId);

    OrderShopDto getDetail(Long orderShopId);

    int updateActualPrice(OrderShop orderShop);

    int sendProduct(OrderShop orderShop);

    OrderShopDto getOrderProduct(String orderNumber);

    int updateOrderShopStatus(String orderNumber);

    Long getOrderShopId(String orderNumber);
}