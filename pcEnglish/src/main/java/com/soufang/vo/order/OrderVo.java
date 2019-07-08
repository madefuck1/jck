package com.soufang.vo.order;

import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderVo {
    private List<OrderShopDto> orderShopDto;

    public List<OrderShopDto> getOrderShopDto() {
        return orderShopDto;
    }

    public void setOrderShopDto(List<OrderShopDto> orderShopDto) {
        this.orderShopDto = orderShopDto;
    }
}
