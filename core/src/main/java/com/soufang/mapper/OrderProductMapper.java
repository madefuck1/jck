package com.soufang.mapper;

import com.soufang.model.OrderProduct;

import java.util.List;

public interface OrderProductMapper {
    int deleteByPrimaryKey(Long orderProductId);

    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    OrderProduct selectByPrimaryKey(Long orderProductId);

    int updateByPrimaryKeySelective(OrderProduct record);

    int updateByPrimaryKey(OrderProduct record);

    List<OrderProduct> getOPByOSId(Long orderShopId);
}