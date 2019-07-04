package com.soufang.vo.orderShop;

import com.soufang.base.dto.order.OrderShopDto;

import java.util.List;

public class OrderShopVo {
    private List<OrderShopDto> data;
    private Integer count;

    public List<OrderShopDto> getData() {
        return data;
    }

    public void setData(List<OrderShopDto> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
