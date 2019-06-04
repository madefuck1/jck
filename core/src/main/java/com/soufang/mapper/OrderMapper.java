package com.soufang.mapper;

import com.soufang.base.Result;
import com.soufang.model.Order;
import com.soufang.model.OrderShop;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderNumber);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderNumber);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    void delOrder(Order order);

    int updateTotalMoney(Order order);
}